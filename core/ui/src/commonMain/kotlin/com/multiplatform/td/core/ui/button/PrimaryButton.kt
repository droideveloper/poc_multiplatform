package com.multiplatform.td.core.ui.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.multiplatform.td.core.ui.TdTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TdPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    iconResource: ImageVector? = null,
    backgroundColor: Color = TdTheme.colors.blacks.primary,
    contentColor: Color = TdTheme.colors.whites.primary,
    border: BorderStroke? = null,
) {
    Button(
        onClick = if (enabled && !loading) onClick else ({}),
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
            .height(TdTheme.dimens.standard48),
        colors = buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor,
            disabledContainerColor = TdTheme.colors.greys.primary,
            disabledContentColor = TdTheme.colors.whites.primary,
        ),
        contentPadding = PaddingValues(
            vertical = TdTheme.dimens.standard0,
            horizontal = TdTheme.dimens.standard16,
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = TdTheme.dimens.standard0,
            pressedElevation = TdTheme.dimens.standard0,
            disabledElevation = TdTheme.dimens.standard0,
            focusedElevation = TdTheme.dimens.standard0,
            hoveredElevation = TdTheme.dimens.standard0,
        ),
        border = border,
    ) {
        if (loading) {
            TdButtonLoadingIndicator(color = contentColor)
        } else {
            if (iconResource != null) {
                TdButtonIcon(iconResource = iconResource, iconSize = TdTheme.dimens.standard20)
            }
            TdButtonText(
                text = text,
                textStyle = TdTheme.typography.titleMedium,
                color = contentColor,
            )
        }
    }
}

@Preview
@Composable
private fun Default() = TdTheme {
    TdPrimaryButton(text = "Action", onClick = {})
}

@Preview
@Composable
private fun WithIcon() = TdTheme {
    TdPrimaryButton(
        text = "Action",
        onClick = {},
        iconResource = Icons.AutoMirrored.Filled.Send,
    )
}

@Preview
@Composable
private fun Disabled() = TdTheme {
    TdPrimaryButton(text = "Action", onClick = {}, enabled = false)
}

@Preview
@Composable
private fun CustomColors() = TdTheme {
    TdPrimaryButton(
        text = "Action",
        onClick = {},
        backgroundColor = TdTheme.colors.oranges.primary,
        contentColor = TdTheme.colors.blacks.primary,
    )
}

@Preview
@Composable
private fun Loading() = TdTheme {
    TdPrimaryButton(text = "Action", onClick = {}, loading = true)
}
