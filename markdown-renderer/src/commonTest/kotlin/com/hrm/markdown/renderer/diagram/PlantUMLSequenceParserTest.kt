package com.hrm.markdown.renderer.diagram

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class PlantUMLSequenceParserTest {

    @Test
    fun should_parse_simple_sequence_diagram() {
        val code = """
            @startuml
            actor User
            User -> Parser : 输入 Markdown
            Parser -> AST : 生成节点树
            AST -> Renderer : 渲染 UI
            @enduml
        """.trimIndent()
        val data = parsePlantUMLSequence(code)
        assertNotNull(data)
        assertEquals(4, data.participants.size)
        assertEquals(3, data.messages.size)
    }

    @Test
    fun should_parse_actor_type() {
        val code = """
            @startuml
            actor User
            User -> System : request
            @enduml
        """.trimIndent()
        val data = parsePlantUMLSequence(code)
        assertNotNull(data)
        val user = data.participants.find { it.id == "User" }
        assertNotNull(user)
        assertEquals(ParticipantType.ACTOR, user.type)
    }

    @Test
    fun should_parse_participant_declaration() {
        val code = """
            @startuml
            participant Alice
            participant Bob
            Alice -> Bob : hello
            @enduml
        """.trimIndent()
        val data = parsePlantUMLSequence(code)
        assertNotNull(data)
        assertEquals(2, data.participants.size)
        assertEquals("Alice", data.participants[0].label)
    }

    @Test
    fun should_parse_dotted_arrow() {
        val code = """
            @startuml
            A --> B : response
            @enduml
        """.trimIndent()
        val data = parsePlantUMLSequence(code)
        assertNotNull(data)
        assertEquals(ArrowStyle.DOTTED, data.messages[0].arrowStyle)
    }

    @Test
    fun should_parse_message_labels() {
        val code = """
            @startuml
            Client -> Server : GET /api/data
            Server -> Client : 200 OK
            @enduml
        """.trimIndent()
        val data = parsePlantUMLSequence(code)
        assertNotNull(data)
        assertEquals("GET /api/data", data.messages[0].label)
        assertEquals("200 OK", data.messages[1].label)
    }

    @Test
    fun should_auto_create_participants_from_messages() {
        val code = """
            @startuml
            A -> B : msg1
            B -> C : msg2
            @enduml
        """.trimIndent()
        val data = parsePlantUMLSequence(code)
        assertNotNull(data)
        assertEquals(3, data.participants.size)
    }

    @Test
    fun should_return_null_for_empty_input() {
        assertNull(parsePlantUMLSequence(""))
    }

    @Test
    fun should_return_null_for_no_participants() {
        val data = parsePlantUMLSequence("@startuml\n@enduml")
        assertNull(data)
    }

    @Test
    fun should_parse_demo_plantuml() {
        val code = """
            @startuml
            actor User
            User -> Parser : 输入 Markdown
            Parser -> AST : 生成节点树
            AST -> Renderer : 渲染 UI
            @enduml
        """.trimIndent()
        val data = parsePlantUMLSequence(code)
        assertNotNull(data)
        assertEquals(4, data.participants.size)
        assertEquals(3, data.messages.size)
        assertEquals("User", data.participants[0].id)
        assertEquals(ParticipantType.ACTOR, data.participants[0].type)
        assertEquals("输入 Markdown", data.messages[0].label)
    }

    @Test
    fun should_calculate_valid_diagram_size() {
        val code = """
            @startuml
            A -> B : msg
            B -> C : msg
            @enduml
        """.trimIndent()
        val data = parsePlantUMLSequence(code)!!
        val size = calculateSequenceDiagramSize(data) { text ->
            Pair(text.length * 8f, 16f)
        }
        assertTrue(size.width > 0)
        assertTrue(size.height > 0)
    }

    @Test
    fun should_preserve_participant_order() {
        val code = """
            @startuml
            participant C
            participant A
            participant B
            C -> A : msg1
            A -> B : msg2
            @enduml
        """.trimIndent()
        val data = parsePlantUMLSequence(code)
        assertNotNull(data)
        assertEquals("C", data.participants[0].id)
        assertEquals("A", data.participants[1].id)
        assertEquals("B", data.participants[2].id)
    }
}
