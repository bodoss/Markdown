package com.hrm.markdown.renderer.block

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.sp
import com.hrm.latex.renderer.Latex
import com.hrm.latex.renderer.measure.rememberLatexMeasurer
import com.hrm.latex.renderer.model.LatexConfig
import com.hrm.markdown.parser.ast.MathBlock
import com.hrm.markdown.renderer.LocalMarkdownTheme

/**
 * 数学公式块渲染器 ($$...$$)
 * 使用 LaTeX 库渲染数学公式，通过 LatexMeasurer 预测量精确尺寸，避免多余空白。
 */
@Composable
internal fun MathBlockRenderer(
    node: MathBlock,
    modifier: Modifier = Modifier,
) {
    val theme = LocalMarkdownTheme.current
    val latex = node.literal.trim()
    val config = LatexConfig(
        fontSize = (theme.mathFontSize * 1.2f).sp,
    )

    // 使用 LatexMeasurer 精确测量公式高度，避免容器产生多余空白
    val latexMeasurer = rememberLatexMeasurer(config)
    val density = LocalDensity.current
    val dims = latexMeasurer.measure(latex, config)

    val heightModifier = if (dims != null) {
        val heightDp = with(density) { dims.heightPx.toDp() }
        Modifier.height(heightDp)
    } else {
        Modifier
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(theme.codeBlockCornerRadius))
            .background(theme.mathBlockBackground)
            .padding(theme.codeBlockPadding),
        contentAlignment = Alignment.Center,
    ) {
        Latex(
            latex = latex,
            modifier = heightModifier,
            config = config,
        )
    }
}
