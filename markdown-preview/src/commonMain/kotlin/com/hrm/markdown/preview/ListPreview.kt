package com.hrm.markdown.preview

import androidx.compose.runtime.Composable
import com.hrm.markdown.renderer.Markdown

internal val listPreviewGroups = listOf(
    PreviewGroup(
        id = "unordered_list",
        title = "无序列表",
        description = "基础无序列表与嵌套",
        items = listOf(
            PreviewItem(
                id = "basic_ul",
                title = "基础无序列表",
                markdown = """
- 项目一
- 项目二
- 项目三
                """.trimIndent(),
                content = {
                    Markdown(
                        markdown = """
- 项目一
- 项目二
- 项目三
                        """.trimIndent()
                    )
                }
            ),
            PreviewItem(
                id = "nested_ul",
                title = "嵌套无序列表",
                markdown = """
- 项目一
  - 嵌套项目 A
  - 嵌套项目 B
    - 深层嵌套
- 项目二
                """.trimIndent(),
                content = {
                    Markdown(
                        markdown = """
- 项目一
  - 嵌套项目 A
  - 嵌套项目 B
    - 深层嵌套
- 项目二
                        """.trimIndent()
                    )
                }
            ),
        )
    ),
    PreviewGroup(
        id = "ordered_list",
        title = "有序列表",
        description = "基础有序列表与嵌套",
        items = listOf(
            PreviewItem(
                id = "basic_ol",
                title = "基础有序列表",
                markdown = """
1. 第一步
2. 第二步
3. 第三步
                """.trimIndent(),
                content = {
                    Markdown(
                        markdown = """
1. 第一步
2. 第二步
3. 第三步
                        """.trimIndent()
                    )
                }
            ),
            PreviewItem(
                id = "nested_ol",
                title = "嵌套有序列表",
                markdown = """
1. 第一步
   1. 子步骤 A
   2. 子步骤 B
2. 第二步
3. 第三步
                """.trimIndent(),
                content = {
                    Markdown(
                        markdown = """
1. 第一步
   1. 子步骤 A
   2. 子步骤 B
2. 第二步
3. 第三步
                        """.trimIndent()
                    )
                }
            ),
        )
    ),
    PreviewGroup(
        id = "task_list",
        title = "任务列表",
        description = "带复选框的任务列表",
        items = listOf(
            PreviewItem(
                id = "basic_task",
                title = "任务列表",
                markdown = """
- [x] 已完成的任务
- [ ] 待完成的任务
- [x] 另一个已完成的任务
- [ ] 规划中的任务
                """.trimIndent(),
                content = {
                    Markdown(
                        markdown = """
- [x] 已完成的任务
- [ ] 待完成的任务
- [x] 另一个已完成的任务
- [ ] 规划中的任务
                        """.trimIndent()
                    )
                }
            ),
        )
    ),
    PreviewGroup(
        id = "mixed_list",
        title = "混合列表",
        description = "无序与有序列表混合",
        items = listOf(
            PreviewItem(
                id = "mixed",
                title = "混合嵌套",
                markdown = """
1. 有序第一项
   - 无序子项 A
   - 无序子项 B
2. 有序第二项
   1. 有序子步骤
   2. 有序子步骤
- 无序顶层
  1. 有序嵌套
  2. 有序嵌套
                """.trimIndent(),
                content = {
                    Markdown(
                        markdown = """
1. 有序第一项
   - 无序子项 A
   - 无序子项 B
2. 有序第二项
   1. 有序子步骤
   2. 有序子步骤
- 无序顶层
  1. 有序嵌套
  2. 有序嵌套
                        """.trimIndent()
                    )
                }
            ),
        )
    ),
)
