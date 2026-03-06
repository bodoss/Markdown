package com.hrm.markdown.parser.block.postprocessors

import com.hrm.markdown.parser.ast.Document
import com.hrm.markdown.parser.ast.Node

/**
 * 后处理器注册表。
 *
 * 管理所有已注册的 [PostProcessor]，按优先级顺序执行。
 *
 * ## 使用方式
 * ```kotlin
 * // 使用内置默认处理器
 * val registry = PostProcessorRegistry.withDefaults()
 *
 * // 添加自定义处理器
 * registry.register(MyCustomPostProcessor())
 *
 * // 空注册表（不执行任何后处理）
 * val empty = PostProcessorRegistry()
 * ```
 */
class PostProcessorRegistry {
    private val processors = mutableListOf<PostProcessor>()
    private var sorted = false

    fun register(processor: PostProcessor) {
        processors.add(processor)
        sorted = false
    }

    fun registerAll(vararg processors: PostProcessor) {
        this.processors.addAll(processors)
        sorted = false
    }

    /**
     * 按优先级顺序执行所有后处理器。
     */
    fun processAll(document: Document) {
        ensureSorted()
        for (processor in processors) {
            processor.process(document)
        }
    }

    private fun ensureSorted() {
        if (!sorted) {
            processors.sortBy { it.priority }
            sorted = true
        }
    }

    companion object {
        /**
         * 创建预注册了所有内置后处理器的注册表。
         *
         * 内置处理器按优先级排列：
         * 1. [HeadingIdProcessor] (100) — 自动生成标题 ID (slug)
         * 2. [HtmlFilterProcessor] (200) — GFM 禁止的 HTML 标签过滤
         * 3. [AbbreviationProcessor] (300) — 缩写替换
         * 4. [DiagramProcessor] (400) — 围栏代码块 → 图表块转换
         */
        fun withDefaults(): PostProcessorRegistry {
            return PostProcessorRegistry().apply {
                register(HeadingIdProcessor())
                register(HtmlFilterProcessor())
                register(AbbreviationProcessor())
                register(DiagramProcessor())
            }
        }

        /**
         * 从节点中提取纯文本的便捷方法。
         * 委托给 [HeadingIdProcessor.extractPlainText]。
         */
        fun extractPlainText(node: Node): String {
            return HeadingIdProcessor.extractPlainText(node)
        }
    }
}
