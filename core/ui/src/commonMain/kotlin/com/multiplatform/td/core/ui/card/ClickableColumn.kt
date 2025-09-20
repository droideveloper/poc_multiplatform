package com.multiplatform.td.core.ui.card

import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.createRippleModifierNode
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorProducer
import androidx.compose.ui.node.DelegatableNode
import androidx.compose.ui.unit.Dp
import com.multiplatform.td.core.ui.TdTheme

@Composable
fun TdClickableColumn(
    modifier: Modifier = Modifier,
    rippleColor: Color = TdTheme.colors.blacks.light,
    backgroundColor: Color = TdTheme.colors.whites.primary,
    enabled: Boolean = true,
    onClick: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val indicationFactory = remember { RippleIndicationFactory(interactionSource, { rippleColor }) }
    Column(
        modifier = Modifier
            .background(color = backgroundColor)
            .clickable(
                interactionSource = interactionSource,
                indication = indicationFactory,
                enabled = enabled,
                onClick = if (enabled) onClick else ({ /*no-op*/ }),
            )
            .then(modifier),
    ) {
        content()
    }
}

internal class RippleIndicationFactory(
    private val interactionSource: MutableInteractionSource,
    private val colorProducer: ColorProducer,
    private val radius: Dp = Dp.Unspecified,
) : IndicationNodeFactory {

    private val rippleAlpha = RippleAlpha(
        pressedAlpha = 0.24f,
        focusedAlpha = 0.24f,
        draggedAlpha = 0.16f,
        hoveredAlpha = 0.08f,
    )

    override fun create(interactionSource: InteractionSource): DelegatableNode = createRippleModifierNode(
        interactionSource = interactionSource,
        bounded = true,
        radius = radius,
        color = colorProducer,
        rippleAlpha = { rippleAlpha },
    )

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other is RippleIndicationFactory) {
            return other.colorProducer == colorProducer &&
                other.interactionSource == interactionSource
        }
        return false
    }

    override fun hashCode(): Int = interactionSource.hashCode() * 31 + colorProducer.hashCode()
}
