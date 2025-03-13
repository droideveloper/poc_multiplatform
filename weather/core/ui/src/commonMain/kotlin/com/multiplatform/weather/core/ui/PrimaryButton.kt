package com.multiplatform.weather.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.multiplatform.td.core.ui.button.TdPrimaryButton
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun FwPrimaryButton(
    modifier: Modifier = Modifier,
    isEnabled: Boolean,
    text: String,
    onClick: () -> Unit,
) {
    TdPrimaryButton(
        modifier = modifier,
        enabled = isEnabled,
        text = text,
        onClick = onClick,
        backgroundColor = FwTheme.colors.blues.primary,
        contentColor = FwTheme.colors.whites.primary,
    )
}

@Preview
@Composable
private fun FwPrimaryButtonPreview() {
    FwTheme {
        FwPrimaryButton(
            isEnabled = true,
            text = "Action",
            onClick = { },
        )
    }
}