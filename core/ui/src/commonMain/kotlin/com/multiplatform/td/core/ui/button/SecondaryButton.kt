package com.multiplatform.td.core.ui.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ButtonDefaults.outlinedButtonColors
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.multiplatform.td.core.ui.TdTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TdSecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    iconResource: ImageVector? = null,
    contentColor: Color = TdTheme.colors.blacks.primary,
) {
    OutlinedButton(
        onClick = if (enabled && !loading) onClick else ({}),
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
            .height(TdTheme.dimens.standard48),
        colors = outlinedButtonColors(
            contentColor = contentColor,
            disabledContentColor = TdTheme.colors.greys.primary
        ),
        border = BorderStroke(
            width = TdTheme.dimens.standard1,
            color = if (enabled) contentColor else TdTheme.colors.greys.primary,
        ),
        contentPadding = PaddingValues(
            vertical = TdTheme.dimens.standard0,
            horizontal = TdTheme.dimens.standard16,
        )
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
                color = if (enabled) contentColor else TdTheme.colors.greys.primary,
            )
        }
    }
}

@Preview
@Composable
private fun Default() = TdTheme {
    TdSecondaryButton(text = "Action", onClick = {})
}

@Preview
@Composable
private fun WithIcon() = TdTheme {
    TdSecondaryButton(
        text = "Action",
        onClick = {},
        iconResource = Icons.Filled.Share,
        contentColor = TdTheme.colors.greens.primary,
    )
}

@Preview
@Composable
private fun Disabled() = TdTheme {
    TdSecondaryButton(text = "Action", onClick = {}, enabled = false)
}

@Preview
@Composable
private fun CustomColor() = TdTheme {
    TdSecondaryButton(
        text = "Action",
        onClick = {},
        contentColor = TdTheme.colors.oranges.primary,
    )
}

@Preview
@Composable
private fun Loading() = TdTheme {
    TdSecondaryButton(text = "Action", onClick = {}, loading = true)
}
