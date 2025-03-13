package com.multiplatform.td.core.ui.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import com.multiplatform.td.core.ui.TdTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TdCardOutlined(
    modifier: Modifier = Modifier,
    rippleColor: Color = TdTheme.colors.blacks.light,
    backgroundColor: Color = TdTheme.colors.whites.primary,
    borderColor: Color = TdTheme.colors.blues.primary,
    shape: Shape = RoundedCornerShape(TdTheme.dimens.standard12),
    enabled: Boolean = true,
    onClick: () -> Unit = { },
    content: @Composable ColumnScope.() -> Unit,
) {
    OutlinedCard(
        elevation = CardDefaults.emptyElevation(),
        shape = shape,
        border = borderStandard1(borderColor = borderColor),
        modifier = modifier,
    ) {
        TdClickableColumn(
            rippleColor = rippleColor,
            backgroundColor = backgroundColor,
            enabled = enabled,
            onClick = onClick,
            content = content,
        )
    }
}

@Composable
internal fun borderStandard1(borderColor: Color) = BorderStroke(
    width = TdTheme.dimens.standard1,
    color = borderColor,
)

@Composable
internal fun CardDefaults.emptyElevation() = cardElevation(
    defaultElevation = TdTheme.dimens.standard0,
    pressedElevation = TdTheme.dimens.standard0,
    focusedElevation = TdTheme.dimens.standard0,
    hoveredElevation = TdTheme.dimens.standard0,
    draggedElevation = TdTheme.dimens.standard0,
    disabledElevation = TdTheme.dimens.standard0,
)

@Preview
@Composable
private fun Preview() = TdTheme {
    Column(modifier = Modifier.padding(TdTheme.dimens.standard16)) {
        TdCardOutlined(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .padding(TdTheme.dimens.standard32)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                Text(text = "CARD", style = TdTheme.typography.titleMedium)
            }
        }
    }
}
