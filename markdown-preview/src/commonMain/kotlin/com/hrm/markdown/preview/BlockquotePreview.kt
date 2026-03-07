package com.hrm.markdown.preview

import androidx.compose.runtime.Composable
import com.hrm.markdown.renderer.Markdown

internal val blockquotePreviewGroups = listOf(
    PreviewGroup(
        id = "basic_quote",
        title = "基础引用",
        description = "简单引用块与嵌套引用",
        items = listOf(
            PreviewItem(
                id = "simple_quote",
                title = "简单引用",
                content = {
                    Markdown(
                        markdown = """
> 这是一段引用。
> 
> 引用中可以包含 **粗体** 和 *斜体*。
                        """.trimIndent()
                    )
                }
            ),
            PreviewItem(
                id = "nested_quote",
                title = "嵌套引用",
                content = {
                    Markdown(
                        markdown = """
> 第一层引用
> 
> > 第二层嵌套引用
> > 
> > > 第三层嵌套引用
                        """.trimIndent()
                    )
                }
            ),
            PreviewItem(
                id = "quote_with_content",
                title = "引用中包含丰富内容",
                content = {
                    Markdown(
                        markdown = """
> **总结：** KMP 让跨平台开发变得前所未有的简单。
>
> > *"Write once, run anywhere"* —— 这不再是口号，而是 Kotlin Multiplatform 的现实。
>
> 结合 Compose Multiplatform 的声明式 UI，开发者可以用 **一套代码** 覆盖 **所有主流平台**。
                        """.trimIndent()
                    )
                }
            ),
        )
    ),
    PreviewGroup(
        id = "admonition_note",
        title = "NOTE 提示框",
        description = "提示信息 Admonition",
        items = listOf(
            PreviewItem(
                id = "note",
                title = "NOTE",
                content = {
                    Markdown(
                        markdown = """
> [!NOTE]
> 这是一个提示信息，用于展示 Admonition 渲染效果。
> 
> 可以包含多行内容和 **粗体** 等样式。
                        """.trimIndent()
                    )
                }
            ),
        )
    ),
    PreviewGroup(
        id = "admonition_warning",
        title = "WARNING 警告框",
        description = "警告信息 Admonition",
        items = listOf(
            PreviewItem(
                id = "warning",
                title = "WARNING",
                content = {
                    Markdown(
                        markdown = """
> [!WARNING]
> 这是一个警告信息。请注意潜在的风险。
                        """.trimIndent()
                    )
                }
            ),
        )
    ),
    PreviewGroup(
        id = "admonition_tip",
        title = "TIP 技巧框",
        description = "技巧提示 Admonition",
        items = listOf(
            PreviewItem(
                id = "tip",
                title = "TIP",
                content = {
                    Markdown(
                        markdown = """
> [!TIP]
> 这是一个技巧提示。分享实用的小技巧。
                        """.trimIndent()
                    )
                }
            ),
        )
    ),
    PreviewGroup(
        id = "all_admonitions",
        title = "全部 Admonition",
        description = "所有 Admonition 类型对比",
        items = listOf(
            PreviewItem(
                id = "all_types",
                title = "NOTE + WARNING + TIP",
                content = {
                    Markdown(
                        markdown = """
> [!NOTE]
> 这是一个提示信息。

> [!WARNING]
> 这是一个警告信息。

> [!TIP]
> 这是一个技巧提示。
                        """.trimIndent()
                    )
                }
            ),
        )
    ),
)
