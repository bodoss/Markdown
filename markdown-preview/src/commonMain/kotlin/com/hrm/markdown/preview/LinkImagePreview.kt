package com.hrm.markdown.preview

import androidx.compose.runtime.Composable
import com.hrm.markdown.renderer.Markdown

internal val linkImagePreviewGroups = listOf(
    PreviewGroup(
        id = "wiki_link",
        title = "Wiki 链接",
        description = "Obsidian 风格 [[page]] 内部链接",
        items = listOf(
            PreviewItem(
                id = "wiki_basic",
                title = "基础 Wiki 链接",
                content = {
                    Markdown(
                        markdown = "访问 [[首页]] 或 [[设置页面]] 查看更多。"
                    )
                }
            ),
            PreviewItem(
                id = "wiki_with_label",
                title = "带显示文本",
                content = {
                    Markdown(
                        markdown = "这是一个 [[getting-started|快速开始指南]]，也可以查看 [[api-reference|API 参考文档]]。"
                    )
                }
            ),
            PreviewItem(
                id = "wiki_multiple",
                title = "多个 Wiki 链接",
                content = {
                    Markdown(
                        markdown = """
相关笔记：
- [[Kotlin 基础]]
- [[Compose Multiplatform|跨平台 UI]]
- [[协程|Kotlin 协程]]
                        """.trimIndent()
                    )
                }
            ),
        )
    ),
    PreviewGroup(
        id = "links",
        title = "链接",
        description = "普通链接、自动链接",
        items = listOf(
            PreviewItem(
                id = "basic_link",
                title = "基础链接",
                content = {
                    Markdown(markdown = "这是一个 [链接](https://kotlinlang.org \"Kotlin 官网\")。")
                }
            ),
            PreviewItem(
                id = "auto_link",
                title = "自动链接",
                content = {
                    Markdown(markdown = "自动链接：<https://kotlinlang.org>")
                }
            ),
            PreviewItem(
                id = "multiple_links",
                title = "多个链接",
                content = {
                    Markdown(
                        markdown = """
- [Kotlin 官方文档](https://kotlinlang.org/docs/multiplatform.html)
- [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)
- [KMP Awesome 库列表](https://github.com/AAmirr/kmp-awesome)
                        """.trimIndent()
                    )
                }
            ),
        )
    ),
    PreviewGroup(
        id = "images",
        title = "图片",
        description = "基础图片与高级特性",
        items = listOf(
            PreviewItem(
                id = "basic_image",
                title = "基础图片",
                content = {
                    Markdown(markdown = "![Google Logo](https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png)")
                }
            ),
            PreviewItem(
                id = "image_with_size",
                title = "指定尺寸",
                content = {
                    Markdown(markdown = "![风景](https://images.unsplash.com/photo-1472214103451-9374bd1c798e?w=200&h=100&fit=crop =200x100)")
                }
            ),
            PreviewItem(
                id = "image_with_title",
                title = "带标题",
                content = {
                    Markdown(markdown = "![城市夜景](https://images.unsplash.com/photo-1444723121867-7a241cacace9?w=150&h=150&fit=crop =150x150 \"城市夜景\")")
                }
            ),
        )
    ),
    PreviewGroup(
        id = "link_attributes",
        title = "链接高级属性",
        description = "链接后的 {attrs} 属性块",
        items = listOf(
            PreviewItem(
                id = "link_nofollow",
                title = "SEO 属性（nofollow）",
                content = {
                    Markdown(markdown = "外部链接：[Google](https://google.com){rel=\"nofollow\" target=\"_blank\"}")
                }
            ),
            PreviewItem(
                id = "link_download",
                title = "下载属性",
                content = {
                    Markdown(markdown = "下载文件：[下载 PDF](https://example.com/doc.pdf){download=\"文档.pdf\"}")
                }
            ),
            PreviewItem(
                id = "link_with_class",
                title = "带 CSS 类的链接",
                content = {
                    Markdown(markdown = "样式链接：[按钮链接](https://example.com){.btn .primary #main-link}")
                }
            ),
        )
    ),
    PreviewGroup(
        id = "image_attributes",
        title = "图片属性",
        description = "CSS 类、ID、自定义属性",
        items = listOf(
            PreviewItem(
                id = "image_attrs",
                title = "带属性的图片",
                content = {
                    Markdown(
                        markdown = """
带属性：![自然风光](https://images.unsplash.com/photo-1506744038136-46273834b3fb?w=400&h=250&fit=crop){.rounded .shadow loading=lazy}

尺寸 + 属性：![海滨日落](https://images.unsplash.com/photo-1507525428034-b723cf961d3e?w=400&h=200&fit=crop =400x200){.hero-image #main-banner align=center}
                        """.trimIndent()
                    )
                }
            ),
        )
    ),
    PreviewGroup(
        id = "figure",
        title = "Figure / 图片标题",
        description = "独立段落中的图片自动转为 figure + figcaption",
        items = listOf(
            PreviewItem(
                id = "figure_basic",
                title = "基础 Figure",
                content = {
                    Markdown(
                        markdown = """
下面是一张独立的图片，会自动转换为 Figure：

![Google Logo](https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png)

图片上方会显示标题（来自 alt 文本）。
                        """.trimIndent()
                    )
                }
            ),
            PreviewItem(
                id = "figure_with_title",
                title = "带 title 的 Figure",
                content = {
                    Markdown(
                        markdown = """
![风景照片](https://images.unsplash.com/photo-1472214103451-9374bd1c798e?w=400&h=250&fit=crop "美丽的自然风光")
                        """.trimIndent()
                    )
                }
            ),
            PreviewItem(
                id = "figure_inline_not_converted",
                title = "行内图片不转换",
                content = {
                    Markdown(
                        markdown = "行内图片 ![logo](https://www.google.com/favicon.ico) 不会转换为 Figure，因为它不是独立段落。"
                    )
                }
            ),
        )
    ),
)
