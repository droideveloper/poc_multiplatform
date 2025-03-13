package com.multiplatform.td.core.ui.controls

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.semantics.Role
import com.multiplatform.td.core.ui.TdTheme
import com.multiplatform.td.core.ui.card.RippleIndicationFactory
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TdCheckbox(
    checked: Boolean,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onCheckedChange: (Boolean) -> Unit = {},
) {
    Checkable(
        checked = checked,
        modifier = modifier,
        shape = RoundedCornerShape(TdTheme.dimens.standard4),
        enabled = enabled,
        onCheckedChange = onCheckedChange
    )
}

@Composable
fun TdCheckboxRounded(
    checked: Boolean,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onCheckedChange: (Boolean) -> Unit = {},
    borderColor: Color = TdTheme.colors.greys.primary,
) {
    Checkable(
        checked = checked,
        modifier = modifier,
        shape = CircleShape,
        enabled = enabled,
        onCheckedChange = onCheckedChange,
        borderColor = borderColor,
    )
}

@Composable
private fun Checkable(
    checked: Boolean,
    modifier: Modifier,
    shape: Shape,
    onCheckedChange: (Boolean) -> Unit,
    enabled: Boolean = true,
    rippleColor: Color = TdTheme.colors.blacks.primary,
    borderColor: Color = TdTheme.colors.greys.primary,
) {
    val interactionSource =  remember { MutableInteractionSource() }
    val indicationFactory = remember { RippleIndicationFactory(interactionSource, { rippleColor }) }
    Crossfade(
        targetState = checked,
        modifier = Modifier
            .toggleable(
                value = checked,
                role = Role.Checkbox,
                enabled = enabled,
                interactionSource = interactionSource,
                indication = indicationFactory,
            ) {
                onCheckedChange(it)
            }
            .then(modifier)
    ) {
        if (it) CheckboxChecked(shape)
        else CheckboxUnchecked(shape, borderColor)
    }
}

@Composable
private fun CheckboxChecked(shape: Shape) {
    Box(
        modifier = Modifier
            .size(TdTheme.dimens.standard20)
            .border(
                width = TdTheme.dimens.standard1,
                color = TdTheme.colors.oranges.primary,
                shape = shape
            ),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painter = rememberVectorPainter(Icons.Default.Check),
            contentDescription = null,
            tint = TdTheme.colors.oranges.primary,
            modifier = Modifier
                .size(TdTheme.dimens.standard16)
                .clip(shape)
        )
    }
}

@Composable
private fun CheckboxUnchecked(
    shape: Shape,
    borderColor: Color = TdTheme.colors.greys.primary
) {
    Box(
        modifier = Modifier
            .size(TdTheme.dimens.standard20)
            .border(
                width = TdTheme.dimens.standard1,
                color = borderColor,
                shape = shape
            )
    )
}

@Preview()
@Composable
private fun Checked() = TdTheme {
    TdCheckbox(checked = true)
}

@Preview()
@Composable
private fun NotChecked() = TdTheme {
    TdCheckbox(checked = false)
}

@Preview()
@Composable
private fun RoundedChecked() = TdTheme {
    TdCheckboxRounded(checked = true)
}

@Preview()
@Composable
private fun RoundedNotChecked() = TdTheme {
    TdCheckboxRounded(checked = false)
}