package com.hrm.markdown.preview

import androidx.compose.runtime.Composable
import com.hrm.markdown.renderer.Markdown

internal val diagramPreviewGroups = listOf(
    PreviewGroup(
        id = "mermaid",
        title = "Mermaid 流程图",
        description = "Mermaid flowchart 语法",
        items = listOf(
            PreviewItem(
                id = "mermaid_td",
                title = "从上到下流程图",
                content = {
                    Markdown(
                        markdown = """
```mermaid
flowchart TD
    A[Markdown 文本] --> B[BlockParser]
    B --> C[AST 节点树]
    C --> D[InlineParser]
    D --> E[完整 AST]
    E --> F[Compose 渲染]
```
                        """.trimIndent()
                    )
                }
            ),
            PreviewItem(
                id = "mermaid_lr",
                title = "从左到右流程图",
                content = {
                    Markdown(
                        markdown = """
```mermaid
flowchart LR
    A[输入] -->|解析| B(Parser)
    B -->|生成| C{判断类型}
    C -->|块级| D[BlockNode]
    C -->|行内| E[InlineNode]
```
                        """.trimIndent()
                    )
                }
            ),
        )
    ),
    PreviewGroup(
        id = "plantuml",
        title = "PlantUML 时序图",
        description = "PlantUML 序列图语法",
        items = listOf(
            PreviewItem(
                id = "plantuml_basic",
                title = "基础时序图",
                content = {
                    Markdown(
                        markdown = """
```plantuml
@startuml
actor User
User -> Parser : 输入 Markdown
Parser -> AST : 生成节点树
AST -> Renderer : 渲染 UI
@enduml
```
                        """.trimIndent()
                    )
                }
            ),
        )
    ),
)
