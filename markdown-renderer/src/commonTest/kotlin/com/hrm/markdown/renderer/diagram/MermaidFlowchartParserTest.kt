package com.hrm.markdown.renderer.diagram

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class MermaidFlowchartParserTest {

    @Test
    fun should_parse_simple_flowchart_td() {
        val code = """
            flowchart TD
                A --> B
                B --> C
        """.trimIndent()
        val data = parseMermaidFlowchart(code)
        assertNotNull(data)
        assertEquals(FlowDirection.TD, data.direction)
        assertEquals(3, data.nodes.size)
        assertEquals(2, data.edges.size)
    }

    @Test
    fun should_parse_flowchart_lr() {
        val code = """
            flowchart LR
                A --> B
        """.trimIndent()
        val data = parseMermaidFlowchart(code)
        assertNotNull(data)
        assertEquals(FlowDirection.LR, data.direction)
    }

    @Test
    fun should_parse_graph_bt() {
        val code = """
            graph BT
                A --> B
        """.trimIndent()
        val data = parseMermaidFlowchart(code)
        assertNotNull(data)
        assertEquals(FlowDirection.BT, data.direction)
    }

    @Test
    fun should_parse_node_with_rect_label() {
        val code = """
            flowchart TD
                A[Hello World] --> B[Bye]
        """.trimIndent()
        val data = parseMermaidFlowchart(code)
        assertNotNull(data)
        val nodeA = data.nodes.find { it.id == "A" }
        assertNotNull(nodeA)
        assertEquals("Hello World", nodeA.label)
        assertEquals(NodeShape.RECT, nodeA.shape)
    }

    @Test
    fun should_parse_node_with_round_brackets() {
        val code = """
            flowchart TD
                A(Round Node) --> B
        """.trimIndent()
        val data = parseMermaidFlowchart(code)
        assertNotNull(data)
        val nodeA = data.nodes.find { it.id == "A" }
        assertNotNull(nodeA)
        assertEquals("Round Node", nodeA.label)
        assertEquals(NodeShape.ROUND_RECT, nodeA.shape)
    }

    @Test
    fun should_parse_diamond_node() {
        val code = """
            flowchart TD
                A{Decision} --> B
        """.trimIndent()
        val data = parseMermaidFlowchart(code)
        assertNotNull(data)
        val nodeA = data.nodes.find { it.id == "A" }
        assertNotNull(nodeA)
        assertEquals("Decision", nodeA.label)
        assertEquals(NodeShape.DIAMOND, nodeA.shape)
    }

    @Test
    fun should_parse_edge_with_pipe_label() {
        val code = """
            flowchart TD
                A -->|yes| B
        """.trimIndent()
        val data = parseMermaidFlowchart(code)
        assertNotNull(data)
        assertEquals(1, data.edges.size)
        assertEquals("yes", data.edges[0].label)
    }

    @Test
    fun should_parse_dotted_arrow() {
        val code = """
            flowchart TD
                A -.-> B
        """.trimIndent()
        val data = parseMermaidFlowchart(code)
        assertNotNull(data)
        assertEquals(EdgeStyle.DOTTED_ARROW, data.edges[0].style)
    }

    @Test
    fun should_parse_thick_arrow() {
        val code = """
            flowchart TD
                A ==> B
        """.trimIndent()
        val data = parseMermaidFlowchart(code)
        assertNotNull(data)
        assertEquals(EdgeStyle.THICK_ARROW, data.edges[0].style)
    }

    @Test
    fun should_return_null_for_empty_input() {
        assertNull(parseMermaidFlowchart(""))
    }

    @Test
    fun should_return_null_for_non_flowchart() {
        assertNull(parseMermaidFlowchart("sequenceDiagram\nA->>B: Hi"))
    }

    @Test
    fun should_ignore_comments() {
        val code = """
            flowchart TD
                %% This is a comment
                A --> B
        """.trimIndent()
        val data = parseMermaidFlowchart(code)
        assertNotNull(data)
        assertEquals(2, data.nodes.size)
        assertEquals(1, data.edges.size)
    }

    @Test
    fun should_parse_demo_flowchart() {
        val code = """
            flowchart TD
                A[Markdown 文本] --> B[BlockParser]
                B --> C[AST 节点树]
                C --> D[InlineParser]
                D --> E[完整 AST]
                E --> F[Compose 渲染]
        """.trimIndent()
        val data = parseMermaidFlowchart(code)
        assertNotNull(data)
        assertEquals(6, data.nodes.size)
        assertEquals(5, data.edges.size)
        assertEquals("Markdown 文本", data.nodes.find { it.id == "A" }?.label)
        assertEquals("Compose 渲染", data.nodes.find { it.id == "F" }?.label)
    }

    @Test
    fun should_layout_flowchart_with_valid_size() {
        val code = """
            flowchart TD
                A[Start] --> B[End]
        """.trimIndent()
        val data = parseMermaidFlowchart(code)!!
        val (layouts, size) = layoutFlowchart(data) { text ->
            Pair(text.length * 8f, 16f)
        }
        assertEquals(2, layouts.size)
        assertTrue(size.width > 0)
        assertTrue(size.height > 0)
        // TD: first node should be above second
        assertTrue(layouts[0].y < layouts[1].y)
    }

    @Test
    fun should_layout_lr_direction_horizontally() {
        val code = """
            flowchart LR
                A[Start] --> B[End]
        """.trimIndent()
        val data = parseMermaidFlowchart(code)!!
        val (layouts, _) = layoutFlowchart(data) { text ->
            Pair(text.length * 8f, 16f)
        }
        // LR: first node should be left of second
        assertTrue(layouts[0].x < layouts[1].x)
    }

    @Test
    fun should_parse_chain_edges() {
        val code = """
            flowchart TD
                A[Start] --> B[Middle]
                B --> C[End]
                A --> C
        """.trimIndent()
        val data = parseMermaidFlowchart(code)
        assertNotNull(data)
        assertEquals(3, data.nodes.size)
        assertEquals(3, data.edges.size)
    }
}
