package com.hrm.markdown.preview

import androidx.compose.runtime.Composable
import com.hrm.markdown.renderer.Markdown

internal val extendedPreviewGroups = listOf(
    PreviewGroup(
        id = "footnotes",
        title = "脚注",
        description = "脚注引用与定义",
        items = listOf(
            PreviewItem(
                id = "basic_footnote",
                title = "基础脚注",
                content = {
                    Markdown(
                        markdown = """
这是一段带有脚注的文本[^1]。

[^1]: 这是脚注的内容。
                        """.trimIndent()
                    )
                }
            ),
        )
    ),
    PreviewGroup(
        id = "definition_list",
        title = "定义列表",
        description = "术语与定义",
        items = listOf(
            PreviewItem(
                id = "basic_dl",
                title = "基础定义列表",
                content = {
                    Markdown(
                        markdown = """
Kotlin
: 一种现代的编程语言，运行在 JVM 上。

Compose Multiplatform
: JetBrains 的跨平台 UI 框架。

  支持 Android、iOS、Desktop、Web 多平台开发。
                        """.trimIndent()
                    )
                }
            ),
            PreviewItem(
                id = "rich_dl",
                title = "包含代码和列表的定义",
                content = {
                    Markdown(
                        markdown = """
Compose Multiplatform
: JetBrains 的跨平台 UI 框架。

  ```kotlin
  @Composable
  fun Greeting(name: String) {
      Text("Hello, ${'$'}name!")
  }
  ```

  - 声明式 UI
  - 跨平台复用
                        """.trimIndent()
                    )
                }
            ),
        )
    ),
    PreviewGroup(
        id = "emoji",
        title = "Emoji",
        description = "Emoji 短代码支持",
        items = listOf(
            PreviewItem(
                id = "emoji_basic",
                title = "Emoji 短代码",
                content = {
                    Markdown(markdown = "支持 Emoji 短代码：:smile: :heart: :rocket:")
                }
            ),
        )
    ),
    PreviewGroup(
        id = "keyboard",
        title = "键盘按键",
        description = "<kbd> 标签",
        items = listOf(
            PreviewItem(
                id = "kbd_basic",
                title = "键盘快捷键",
                content = {
                    Markdown(markdown = "使用 <kbd>Ctrl</kbd>+<kbd>C</kbd> 复制，<kbd>Ctrl</kbd>+<kbd>V</kbd> 粘贴。")
                }
            ),
        )
    ),
    PreviewGroup(
        id = "abbreviation",
        title = "缩写",
        description = "缩写标签与悬浮提示",
        items = listOf(
            PreviewItem(
                id = "abbr_basic",
                title = "缩写示例",
                content = {
                    Markdown(
                        markdown = """
*[HTML]: Hyper Text Markup Language
*[CSS]: Cascading Style Sheets

HTML 和 CSS 是 Web 开发的基础技术。HTML 定义页面结构，CSS 负责样式。
                        """.trimIndent()
                    )
                }
            ),
        )
    ),
    PreviewGroup(
        id = "custom_container",
        title = "自定义容器",
        description = "::: 围栏容器与嵌套",
        items = listOf(
            PreviewItem(
                id = "container_note",
                title = "Note 容器",
                content = {
                    Markdown(
                        markdown = """
::: note "提示信息"
这是一个使用 `:::` 围栏语法创建的自定义容器。

支持**完整的 Markdown 语法**，包括列表：
- 项目一
- 项目二
:::
                        """.trimIndent()
                    )
                }
            ),
            PreviewItem(
                id = "container_warning",
                title = "Warning 容器",
                content = {
                    Markdown(
                        markdown = """
::: warning
未指定标题时，容器类型名作为默认标题显示。
:::
                        """.trimIndent()
                    )
                }
            ),
            PreviewItem(
                id = "container_nested",
                title = "嵌套容器",
                content = {
                    Markdown(
                        markdown = """
:::: card
::: note
容器支持**嵌套**，外层使用更多冒号（`::::`）。
:::
::::
                        """.trimIndent()
                    )
                }
            ),
        )
    ),
    PreviewGroup(
        id = "thematic_break",
        title = "分隔线",
        description = "水平分隔线",
        items = listOf(
            PreviewItem(
                id = "hr",
                title = "分隔线",
                content = {
                    Markdown(
                        markdown = """
上面的内容

---

下面的内容
                        """.trimIndent()
                    )
                }
            ),
        )
    ),
)
