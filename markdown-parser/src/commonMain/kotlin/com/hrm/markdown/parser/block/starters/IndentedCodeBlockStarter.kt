package com.hrm.markdown.parser.block.starters

import com.hrm.markdown.parser.LineRange
import com.hrm.markdown.parser.ast.IndentedCodeBlock
import com.hrm.markdown.parser.block.OpenBlock
import com.hrm.markdown.parser.core.LineCursor

/**
 * 缩进代码块开启器：4 个空格或 1 个制表符缩进。
 * 优先级最低，不能中断段落。
 */
internal class IndentedCodeBlockStarter : BlockStarter {
    override val priority: Int = 600
    override val canInterruptParagraph: Boolean = false

    override fun tryStart(cursor: LineCursor, lineIdx: Int, tip: OpenBlock): OpenBlock? {
        if (tip.paragraphContent != null) return null
        val indent = cursor.advanceSpaces()
        if (indent < 4) return null

        val block = IndentedCodeBlock()
        block.lineRange = LineRange(lineIdx, lineIdx + 1)

        val ob = OpenBlock(block, contentStartLine = lineIdx, lastLineIndex = lineIdx)
        ob.starterTag = this::class.simpleName
        return ob
    }
}
