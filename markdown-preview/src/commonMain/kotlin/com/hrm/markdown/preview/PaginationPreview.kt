package com.hrm.markdown.preview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hrm.markdown.renderer.Markdown

internal val paginationPreviewGroups = listOf(
    PreviewGroup(
        id = "pagination_demo",
        title = "分页加载演示",
        description = "超长文档的懒加载分页渲染",
        items = listOf(
            PreviewItem(
                id = "pagination_200",
                title = "200 段落分页加载",
                content = { PaginationDemo() }
            ),
        )
    ),
    PreviewGroup(
        id = "pagination_config",
        title = "不同初始加载数",
        description = "调整 initialBlockCount 参数",
        items = listOf(
            PreviewItem(
                id = "pagination_20",
                title = "初始 20 块",
                content = { PaginationDemoWithConfig(blockCount = 20) }
            ),
            PreviewItem(
                id = "pagination_100",
                title = "初始 100 块",
                content = { PaginationDemoWithConfig(blockCount = 100) }
            ),
        )
    ),
)

@Composable
private fun PaginationDemo() {
    val longDoc = buildString {
        repeat(200) { append("## 段落 $it\n\n这是第 $it 段内容。".repeat(3) + "\n\n") }
    }
    Column {
        Text(
            "分页加载 Demo - 200 个段落",
            Modifier.padding(16.dp),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyMedium
        )
        Markdown(
            longDoc,
            Modifier.fillMaxSize().padding(horizontal = 16.dp),
            enablePagination = true,
            initialBlockCount = 50
        )
    }
}

@Composable
private fun PaginationDemoWithConfig(blockCount: Int) {
    val longDoc = buildString {
        repeat(100) { append("## 段落 $it\n\n这是第 $it 段内容，用于测试 initialBlockCount=$blockCount 的效果。\n\n") }
    }
    Column {
        Text(
            "分页加载 - initialBlockCount=$blockCount",
            Modifier.padding(16.dp),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyMedium
        )
        Markdown(
            longDoc,
            Modifier.fillMaxSize().padding(horizontal = 16.dp),
            enablePagination = true,
            initialBlockCount = blockCount
        )
    }
}
