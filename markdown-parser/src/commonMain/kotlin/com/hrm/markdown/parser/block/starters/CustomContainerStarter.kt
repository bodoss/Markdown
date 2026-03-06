package com.hrm.markdown.parser.block.starters

import com.hrm.markdown.parser.LineRange
import com.hrm.markdown.parser.ast.CustomContainer
import com.hrm.markdown.parser.block.OpenBlock
import com.hrm.markdown.parser.core.LineCursor

/**
 * 自定义容器块开启器：`::: type "title" {.class #id}` 到 `:::`。
 */
internal class CustomContainerStarter : BlockStarter {
    override val priority: Int = 300
    override val canInterruptParagraph: Boolean = true

    override fun tryStart(cursor: LineCursor, lineIdx: Int, tip: OpenBlock): OpenBlock? {
        val indent = cursor.advanceSpaces(3)
        if (cursor.isAtEnd) return null

        if (cursor.peek() != ':') return null

        var colonCount = 0
        val snap = cursor.snapshot()
        while (!cursor.isAtEnd && cursor.peek() == ':') {
            cursor.advance()
            colonCount++
        }
        if (colonCount < 3) return null

        val rest = cursor.rest().trim()
        cursor.advance(cursor.remaining)

        var type = ""
        var title = ""
        var cssClasses = emptyList<String>()
        var cssId: String? = null

        if (rest.isNotEmpty()) {
            val parsed = parseContainerInfo(rest)
            type = parsed.type
            title = parsed.title
            cssClasses = parsed.cssClasses
            cssId = parsed.cssId
        }

        val block = CustomContainer(
            type = type,
            title = title,
            cssClasses = cssClasses,
            cssId = cssId,
        )
        block.lineRange = LineRange(lineIdx, lineIdx + 1)

        val ob = OpenBlock(block, contentStartLine = lineIdx, lastLineIndex = lineIdx)
        ob.fenceChar = ':'
        ob.fenceLength = colonCount
        ob.fenceIndent = indent
        ob.starterTag = this::class.simpleName
        return ob
    }

    private data class ContainerInfo(
        val type: String,
        val title: String,
        val cssClasses: List<String>,
        val cssId: String?,
    )

    private fun parseContainerInfo(info: String): ContainerInfo {
        var remaining = info
        var type = ""
        var title = ""
        val cssClasses = mutableListOf<String>()
        var cssId: String? = null

        val attrMatch = CONTAINER_ATTR_REGEX.find(remaining)
        if (attrMatch != null) {
            val attrContent = attrMatch.groupValues[1]
            CONTAINER_CLASS_REGEX.findAll(attrContent).forEach {
                cssClasses.add(it.groupValues[1])
            }
            CONTAINER_ID_REGEX.find(attrContent)?.let {
                cssId = it.groupValues[1]
            }
            remaining = remaining.removeRange(attrMatch.range).trim()
        }

        val titleMatch = CONTAINER_TITLE_REGEX.find(remaining)
        if (titleMatch != null) {
            title = titleMatch.groupValues[1].ifEmpty { titleMatch.groupValues[2] }
            remaining = remaining.removeRange(titleMatch.range).trim()
        }

        type = remaining.trim().split("\\s+".toRegex()).firstOrNull() ?: ""

        return ContainerInfo(type, title, cssClasses, cssId)
    }

    companion object {
        private val CONTAINER_ATTR_REGEX = Regex("\\{([^}]+)\\}")
        private val CONTAINER_CLASS_REGEX = Regex("\\.([a-zA-Z][a-zA-Z0-9_-]*)")
        private val CONTAINER_ID_REGEX = Regex("#([a-zA-Z][a-zA-Z0-9_-]*)")
        private val CONTAINER_TITLE_REGEX = Regex("\"([^\"]*)\"|'([^']*)'")
    }
}
