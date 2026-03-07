package com.hrm.markdown.preview

import androidx.compose.runtime.Composable
import com.hrm.markdown.renderer.Markdown

internal val tablePreviewGroups = listOf(
    PreviewGroup(
        id = "basic_table",
        title = "基础表格",
        description = "简单 GFM 表格",
        items = listOf(
            PreviewItem(
                id = "simple_table",
                title = "简单表格",
                content = {
                    Markdown(
                        markdown = """
| 名称 | 版本 | 说明 |
|------|------|------|
| Kotlin | 2.0 | 编程语言 |
| Compose | 1.6 | UI 框架 |
| Ktor | 2.3 | HTTP 客户端 |
                        """.trimIndent()
                    )
                }
            ),
        )
    ),
    PreviewGroup(
        id = "aligned_table",
        title = "对齐方式",
        description = "左对齐、居中、右对齐",
        items = listOf(
            PreviewItem(
                id = "alignment",
                title = "不同对齐方式",
                content = {
                    Markdown(
                        markdown = """
| 功能 | 状态 | 说明 |
|:-----|:----:|-----:|
| 标题 | ✅ | ATX & Setext |
| 代码块 | ✅ | 围栏 & 缩进 |
| 列表 | ✅ | 有序 & 无序 & 任务 |
| 数学公式 | ✅ | 行内 & 块级 |
| 表格 | ✅ | GFM 扩展 |
                        """.trimIndent()
                    )
                }
            ),
        )
    ),
    PreviewGroup(
        id = "complex_table",
        title = "复杂表格",
        description = "包含行内样式的表格",
        items = listOf(
            PreviewItem(
                id = "styled_table",
                title = "带样式的表格",
                content = {
                    Markdown(
                        markdown = """
| 功能 | Android | iOS | Desktop | Web | 说明 |
|:---|:---:|:---:|:---:|:---:|:--- |
| Compose UI | ✅ | ✅ | ✅ | ✅ | 全平台统一 UI |
| 协程 | ✅ | ✅ | ✅ | ✅ | kotlinx.coroutines |
| 网络请求 | ✅ | ✅ | ✅ | ✅ | Ktor Client |
| 数据库 | ✅ | ✅ | ✅ | ⚠️ | SQLDelight / Room |
| 文件系统 | ✅ | ✅ | ✅ | ❌ | okio |
| 蓝牙/NFC | ✅ | ✅ | ⚠️ | ❌ | 需平台特定实现 |
                        """.trimIndent()
                    )
                }
            ),
        )
    ),
)
