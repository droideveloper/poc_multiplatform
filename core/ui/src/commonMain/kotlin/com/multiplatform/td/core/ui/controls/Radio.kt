package com.multiplatform.td.core.ui.controls

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import com.multiplatform.td.core.ui.TdTheme
import com.multiplatform.td.core.ui.card.RippleIndicationFactory
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TdRadio(
    selected: Boolean,
    onClick: (() -> Unit)?,
    rippleColor: Color = TdTheme.colors.blacks.primary,
    rippleRadius: Dp = TdTheme.dimens.standard32,
    modifier: Modifier = Modifier,
) {
    val interactionSource =  remember { MutableInteractionSource() }
    val indicationFactory = remember {
        RippleIndicationFactory(interactionSource, { rippleColor }, rippleRadius)
    }
    val selectableModifier = if (onClick != null) {
        Modifier
            .selectable(
                selected = selected,
                onClick = onClick,
                enabled = true,
                role = Role.RadioButton,
                interactionSource = interactionSource,
                indication = indicationFactory
            )
    } else Modifier

    Crossfade(targetState = selected) {
        Box(
            modifier = selectableModifier
                .size(TdTheme.dimens.standard48)
                .then(modifier),
            contentAlignment = Alignment.Center,
        ) {
            if (it) RadioChecked()
            else RadioUnchecked()
        }
    }
}

@Composable
private fun RadioChecked() {
    Box(
        modifier = Modifier
            .size(TdTheme.dimens.standard24)
            .clip(CircleShape)
            .background(TdTheme.colors.oranges.primary),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(TdTheme.dimens.standard12)
                .clip(CircleShape)
                .background(TdTheme.colors.oranges.secondary)
        )
    }
}

@Composable
private fun RadioUnchecked() {
    Box(
        modifier = Modifier
            .size(TdTheme.dimens.standard24)
            .border(
                width = TdTheme.dimens.standard1,
                color = TdTheme.colors.greys.primary,
                shape = CircleShape
            )
    )
}

@Preview()
@Composable
private fun Checked() = TdTheme {
    TdRadio(selected = true, onClick = null)
}

@Preview()
@Composable
private fun NotChecked() = TdTheme {
    TdRadio(selected = false, onClick = null)
}