package com.hrm.markdown.preview

import androidx.compose.runtime.Composable
import com.hrm.markdown.renderer.Markdown

internal val textStylePreviewGroups = listOf(
    PreviewGroup(
        id = "basic_emphasis",
        title = "基础强调",
        description = "粗体、斜体、粗斜体",
        items = listOf(
            PreviewItem(
                id = "bold",
                title = "粗体",
                markdown = "这是 **粗体文本** 示例",
                content = { Markdown(markdown = "这是 **粗体文本** 示例") }
            ),
            PreviewItem(
                id = "italic",
                title = "斜体",
                markdown = "这是 *斜体文本* 示例",
                content = { Markdown(markdown = "这是 *斜体文本* 示例") }
            ),
            PreviewItem(
                id = "bold_italic",
                title = "粗斜体",
                markdown = "这是 ***粗斜体文本*** 示例",
                content = { Markdown(markdown = "这是 ***粗斜体文本*** 示例") }
            ),
            PreviewItem(
                id = "mixed_emphasis",
                title = "混合强调",
                markdown = "**粗体中的 *斜体*** 和 *斜体中的 **粗体***",
                content = { Markdown(markdown = "**粗体中的 *斜体*** 和 *斜体中的 **粗体***") }
            ),
        )
    ),
    PreviewGroup(
        id = "strikethrough_highlight",
        title = "删除线与高亮",
        description = "删除线、高亮文本",
        items = listOf(
            PreviewItem(
                id = "strikethrough",
                title = "删除线",
                markdown = "这是 ~~删除线文本~~ 示例",
                content = { Markdown(markdown = "这是 ~~删除线文本~~ 示例") }
            ),
            PreviewItem(
                id = "highlight",
                title = "高亮",
                markdown = "这是 ==高亮文本== 示例",
                content = { Markdown(markdown = "这是 ==高亮文本== 示例") }
            ),
            PreviewItem(
                id = "insert",
                title = "插入文本",
                markdown = "这是 ++插入文本++ 示例",
                content = { Markdown(markdown = "这是 ++插入文本++ 示例") }
            ),
        )
    ),
    PreviewGroup(
        id = "subscript_superscript",
        title = "上标与下标",
        description = "上标、下标语法",
        items = listOf(
            PreviewItem(
                id = "subscript",
                title = "下标",
                markdown = "H~2~O 是水的化学式",
                content = { Markdown(markdown = "H~2~O 是水的化学式") }
            ),
            PreviewItem(
                id = "superscript",
                title = "上标",
                markdown = "x^2^ + y^2^ = z^2^",
                content = { Markdown(markdown = "x^2^ + y^2^ = z^2^") }
            ),
        )
    ),
    PreviewGroup(
        id = "inline_code",
        title = "行内代码",
        description = "行内代码标记",
        items = listOf(
            PreviewItem(
                id = "inline_code_basic",
                title = "基础行内代码",
                markdown = "使用 `println()` 输出信息",
                content = { Markdown(markdown = "使用 `println()` 输出信息") }
            ),
            PreviewItem(
                id = "inline_code_in_sentence",
                title = "句子中的行内代码",
                markdown = "Kotlin 的 `val` 是不可变变量，`var` 是可变变量，`fun` 定义函数",
                content = { Markdown(markdown = "Kotlin 的 `val` 是不可变变量，`var` 是可变变量，`fun` 定义函数") }
            ),
        )
    ),
    PreviewGroup(
        id = "combined_styles",
        title = "组合样式",
        description = "多种行内样式组合使用",
        items = listOf(
            PreviewItem(
                id = "all_styles",
                title = "全部行内样式",
                markdown = """
这段文字包含 **粗体**、*斜体*、***粗斜体***、~~删除线~~、`行内代码` 和 ==高亮文本== 等样式。

还支持 H~2~O 下标和 x^2^ 上标，以及 ++插入文本++。
                """.trimIndent(),
                content = {
                    Markdown(
                        markdown = """
这段文字包含 **粗体**、*斜体*、***粗斜体***、~~删除线~~、`行内代码` 和 ==高亮文本== 等样式。

还支持 H~2~O 下标和 x^2^ 上标，以及 ++插入文本++。
                        """.trimIndent()
                    )
                }
            ),
        )
    ),
)
