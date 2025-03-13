package com.multiplatform.td.core.ui.button

import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import com.multiplatform.td.core.ui.TdTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TdTextLinkBlack(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    textStyle: TextStyle = TdTheme.typography.titleTiny,
) {
    TdTextLinkButton(
        text = text,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        textColor = TdTheme.colors.blacks.primary,
        pressedTextColor = TdTheme.colors.blues.secondary,
        textStyle = textStyle,
    )
}

@Composable
fun TdTextLinkBlue(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    textColor: Color = TdTheme.colors.blues.primary,
    pressedTextColor: Color = TdTheme.colors.blues.secondary,
    textStyle: TextStyle = TdTheme.typography.titleTiny,
) {
    TdTextLinkButton(
        text = text,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        textColor = textColor,
        pressedTextColor = pressedTextColor,
        textStyle = textStyle,
    )
}

@Composable
fun TdTextLinkButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    textColor: Color = TdTheme.colors.blues.primary,
    pressedTextColor: Color = TdTheme.colors.blues.secondary,
    textStyle: TextStyle = TdTheme.typography.titleTiny,
    indication: Indication? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressedState = interactionSource.collectIsPressedAsState()

    Text(
        modifier = Modifier
            .clickable(
                interactionSource = interactionSource,
                indication = indication,
                onClick = if (enabled) onClick else ({}),
            )
            .testTag("button_text")
            .then(modifier),
        text = text,
        color = if (enabled) {
            if (pressedState.value)
                pressedTextColor
            else
                textColor
        } else
            TdTheme.colors.greys.primary,
        style = textStyle.copy(textDecoration = TextDecoration.Underline),
    )
}

@Preview
@Composable
private fun DefaultBlack() = TdTheme {
    TdTextLinkBlack(
        text = "Action",
        onClick = {},
    )
}

@Preview
@Composable
private fun DefaultBlue() = TdTheme {
    TdTextLinkBlue(
        text = "Action",
        onClick = {},
    )
}

@Preview
@Composable
private fun Disabled() = TdTheme {
    TdTextLinkButton(
        text = "Action",
        onClick = {},
        enabled = false,
    )
}

