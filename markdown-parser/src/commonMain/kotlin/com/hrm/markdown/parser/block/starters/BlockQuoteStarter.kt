package com.hrm.markdown.parser.block.starters

import com.hrm.markdown.parser.LineRange
import com.hrm.markdown.parser.ast.BlockQuote
import com.hrm.markdown.parser.block.OpenBlock
import com.hrm.markdown.parser.core.LineCursor

/**
 * 块引用开启器：以 `>` 为前缀的行。
 */
internal class BlockQuoteStarter : BlockStarter {
    override val priority: Int = 410
    override val canInterruptParagraph: Boolean = true

    override fun tryStart(cursor: LineCursor, lineIdx: Int, tip: OpenBlock): OpenBlock? {
        val indent = cursor.advanceSpaces(3)
        if (cursor.isAtEnd || cursor.peek() != '>') return null

        cursor.advance()
        if (!cursor.isAtEnd && cursor.peek() == ' ') {
            cursor.advance()
        }

        val bq = BlockQuote()
        bq.lineRange = LineRange(lineIdx, lineIdx + 1)

        val ob = OpenBlock(bq, contentStartLine = lineIdx, lastLineIndex = lineIdx)
        ob.starterTag = this::class.simpleName
        return ob
    }
}
