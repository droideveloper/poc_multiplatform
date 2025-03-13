package com.multiplatform.td.core.ui.card

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import com.multiplatform.td.core.ui.TdTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TdCard(
    modifier: Modifier = Modifier,
    rippleColor: Color = TdTheme.colors.blacks.light,
    shape: Shape = RoundedCornerShape(TdTheme.dimens.standard12),
    containerColor: Color = TdTheme.colors.whites.primary,
    enabled: Boolean = true,
    onClick: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit,
) {
    ElevatedCard(
        elevation = CardDefaults.defaultElevation(),
        shape = shape,
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
        ),
        modifier = modifier,
    ) {
        TdClickableColumn(
            rippleColor = rippleColor,
            backgroundColor = containerColor,
            enabled = enabled,
            onClick = onClick,
            content = content,
        )
    }
}

@Composable
internal fun CardDefaults.defaultElevation() = cardElevation(
    defaultElevation = TdTheme.dimens.standard8,
    pressedElevation = TdTheme.dimens.standard8,
    focusedElevation = TdTheme.dimens.standard8,
    hoveredElevation = TdTheme.dimens.standard8,
    draggedElevation = TdTheme.dimens.standard8,
    disabledElevation = TdTheme.dimens.standard8,
)

@Preview
@Composable
private fun Preview() = TdTheme {
    Column(modifier = Modifier.padding(TdTheme.dimens.standard16)) {
        TdCard(modifier = Modifier.fillMaxWidth()) {
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
