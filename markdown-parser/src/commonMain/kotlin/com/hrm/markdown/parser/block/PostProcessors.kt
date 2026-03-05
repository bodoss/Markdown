package com.hrm.markdown.parser.block

import com.hrm.markdown.parser.ast.*

/**
 * 块解析后处理器：在块结构和行内解析完成后对 AST 进行变换。
 *
 * 包含：
 * - 自动生成标题 ID（slug）
 * - GFM 禁止的 HTML 标签过滤
 * - 缩写替换
 * - 围栏代码块 → 图表块转换
 */
internal object PostProcessors {

    // ────── 标题 ID 生成 ──────

    /**
     * 自动为所有标题生成 ID（slug），基于标题文本内容。
     * 已有 customId 的标题不会被覆盖。
     */
    fun generateHeadingIds(doc: Document) {
        val usedIds = mutableMapOf<String, Int>()
        for (child in doc.children) {
            generateHeadingIdsRecursive(child, usedIds)
        }
    }

    private fun generateHeadingIdsRecursive(node: Node, usedIds: MutableMap<String, Int>) {
        when (node) {
            is Heading -> {
                if (node.customId == null) {
                    val text = extractPlainText(node)
                    val slug = generateSlug(text)
                    node.autoId = deduplicateId(slug, usedIds)
                } else {
                    // 记录 customId 以避免重复
                    usedIds[node.customId!!] = (usedIds[node.customId!!] ?: 0) + 1
                }
            }
            is SetextHeading -> {
                val text = extractPlainText(node)
                val slug = generateSlug(text)
                node.autoId = deduplicateId(slug, usedIds)
            }
            is ContainerNode -> {
                for (child in node.children) {
                    generateHeadingIdsRecursive(child, usedIds)
                }
            }
            else -> {}
        }
    }

    /**
     * 从节点中提取纯文本（递归提取所有 Text 节点的内容）。
     */
    internal fun extractPlainText(node: Node): String {
        return when (node) {
            is Text -> node.literal
            is InlineCode -> node.literal
            is Emoji -> node.literal
            is EscapedChar -> node.literal
            is HtmlEntity -> node.resolved.ifEmpty { node.literal }
            is SoftLineBreak -> " "
            is HardLineBreak -> " "
            is ContainerNode -> node.children.joinToString("") { extractPlainText(it) }
            else -> ""
        }
    }

    /**
     * 将文本转换为 URL 友好的 slug。
     * 规则：小写化 → 非字母数字替换为连字符 → 去除首尾/连续连字符。
     */
    private fun generateSlug(text: String): String {
        return text.lowercase()
            .replace(Regex("[^\\w\\u4e00-\\u9fff-]"), "-")  // 保留中文字符
            .replace(Regex("-+"), "-")
            .trim('-')
            .ifEmpty { "heading" }
    }

    private fun deduplicateId(slug: String, usedIds: MutableMap<String, Int>): String {
        val count = usedIds[slug]
        return if (count == null) {
            usedIds[slug] = 1
            slug
        } else {
            usedIds[slug] = count + 1
            val newId = "$slug-$count"
            usedIds[newId] = 1
            newId
        }
    }

    // ────── GFM HTML 过滤 ──────

    /**
     * GFM：过滤禁止的原始 HTML 标签。
     * 将 `<script>`, `<textarea>`, `<style>` 等危险标签内容替换为注释。
     */
    fun filterDisallowedHtml(doc: Document) {
        for (child in doc.children.toList()) {
            filterDisallowedHtmlRecursive(child)
        }
    }

    private fun filterDisallowedHtmlRecursive(node: Node) {
        when (node) {
            is HtmlBlock -> {
                val filtered = filterGfmDisallowedTags(node.literal)
                if (filtered != node.literal) {
                    node.literal = filtered
                }
            }
            is InlineHtml -> {
                val filtered = filterGfmDisallowedTags(node.literal)
                if (filtered != node.literal) {
                    node.literal = filtered
                }
            }
            is ContainerNode -> {
                for (child in node.children.toList()) {
                    filterDisallowedHtmlRecursive(child)
                }
            }
            else -> {}
        }
    }

    private fun filterGfmDisallowedTags(html: String): String {
        return GFM_DISALLOWED_TAG_REGEX.replace(html) { match ->
            "<!-- ${match.value} (filtered) -->"
        }
    }

    // ────── 缩写替换 ──────

    /**
     * 将缩写定义应用到文档中的 Text 节点。
     * 遍历所有 Text 节点，将匹配的缩写词替换为 Abbreviation 节点。
     */
    fun applyAbbreviations(doc: Document) {
        if (doc.abbreviationDefinitions.isEmpty()) return
        // 按长度降序排列，优先匹配较长的缩写
        val abbrs = doc.abbreviationDefinitions.values.sortedByDescending { it.abbreviation.length }
        applyAbbreviationsRecursive(doc, abbrs)
    }

    private fun applyAbbreviationsRecursive(node: Node, abbrs: List<AbbreviationDefinition>) {
        if (node is ContainerNode) {
            val children = node.children.toList()
            for (child in children) {
                if (child is Text) {
                    replaceAbbreviationsInText(node, child, abbrs)
                } else {
                    applyAbbreviationsRecursive(child, abbrs)
                }
            }
        }
    }

    private fun replaceAbbreviationsInText(
        parent: ContainerNode,
        textNode: Text,
        abbrs: List<AbbreviationDefinition>
    ) {
        var text = textNode.literal
        val replacements = mutableListOf<Triple<Int, Int, AbbreviationDefinition>>() // start, end, def

        for (def in abbrs) {
            val abbr = def.abbreviation
            var searchFrom = 0
            while (true) {
                val idx = text.indexOf(abbr, searchFrom)
                if (idx < 0) break
                // 确保是词边界
                val before = if (idx > 0) text[idx - 1] else ' '
                val after = if (idx + abbr.length < text.length) text[idx + abbr.length] else ' '
                if (!before.isLetterOrDigit() && !after.isLetterOrDigit()) {
                    replacements.add(Triple(idx, idx + abbr.length, def))
                }
                searchFrom = idx + abbr.length
            }
        }

        if (replacements.isEmpty()) return

        // 去除重叠的替换，按位置排序
        val sorted = replacements.sortedBy { it.first }
        val filtered = mutableListOf<Triple<Int, Int, AbbreviationDefinition>>()
        var lastEnd = 0
        for (r in sorted) {
            if (r.first >= lastEnd) {
                filtered.add(r)
                lastEnd = r.second
            }
        }

        // 构建替换后的节点列表
        val newNodes = mutableListOf<Node>()
        var pos = 0
        for ((start, end, def) in filtered) {
            if (start > pos) {
                newNodes.add(Text(text.substring(pos, start)))
            }
            newNodes.add(Abbreviation(abbreviation = def.abbreviation, fullText = def.fullText))
            pos = end
        }
        if (pos < text.length) {
            newNodes.add(Text(text.substring(pos)))
        }

        // 替换原 Text 节点
        val idx = parent.children.indexOf(textNode)
        if (idx >= 0) {
            parent.removeChild(textNode)
            for ((i, n) in newNodes.withIndex()) {
                parent.insertChild(idx + i, n)
            }
        }
    }

    // ────── 图表块转换 ──────

    /**
     * 后处理：将 info string 为 mermaid/plantuml 等的 FencedCodeBlock 转换为 DiagramBlock。
     */
    fun convertDiagramBlocks(doc: Document) {
        convertDiagramBlocksRecursive(doc)
    }

    private fun convertDiagramBlocksRecursive(node: Node) {
        if (node is ContainerNode) {
            val children = node.children.toList()
            for (child in children) {
                if (child is FencedCodeBlock && child.language.lowercase() in DIAGRAM_LANGUAGES) {
                    val diagram = DiagramBlock(
                        diagramType = child.language.lowercase(),
                        literal = child.literal,
                    )
                    diagram.lineRange = child.lineRange
                    diagram.sourceRange = child.sourceRange
                    diagram.contentHash = child.contentHash
                    node.replaceChild(child, diagram)
                } else {
                    convertDiagramBlocksRecursive(child)
                }
            }
        }
    }

    // ────── 常量 ──────

    /** GFM 禁止的 HTML 标签 */
    private val GFM_DISALLOWED_TAG_REGEX = Regex(
        "<(title|textarea|style|xmp|iframe|noembed|noframes|script|plaintext)(\\s[^>]*)?>",
        RegexOption.IGNORE_CASE
    )

    /** 识别为图表块的围栏代码块语言标识 */
    private val DIAGRAM_LANGUAGES = setOf(
        "mermaid", "plantuml", "dot", "graphviz", "ditaa",
        "flowchart", "sequence", "gantt", "pie", "mindmap",
    )
}
