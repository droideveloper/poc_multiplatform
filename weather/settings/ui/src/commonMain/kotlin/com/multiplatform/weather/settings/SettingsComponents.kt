package com.multiplatform.weather.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonColors
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.SingleChoiceSegmentedButtonRowScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import com.multiplatform.weather.core.measure.Speed
import com.multiplatform.weather.core.ui.FwTheme
import com.multiplatform.weather.core.ui.selectDayBackground
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun SingleChoiceSegmentedButtonRowScope.FwSegmentedButton(
    modifier: Modifier = Modifier.testTag("segmented_button"),
    index: Int,
    count: Int,
    isSelected: Boolean,
    text: String,
    onClick: () -> Unit = {},
) {
    val colors = selectSegmentedColors()
    SegmentedButton(
        modifier = modifier,
        shape = SegmentedButtonDefaults.itemShape(index, count),
        selected = isSelected,
        onClick = onClick,
        colors = colors,
    ) {
        Text(
            modifier = Modifier.testTag("button_text"),
            text = text,
            style = FwTheme.typography.bodySecondary.copy(
                fontWeight = FontWeight.SemiBold,
                color = if (isSelected) colors.activeContentColor else colors.inactiveContentColor,
            ),
        )
    }
}

@Composable
private fun selectSegmentedColors(): SegmentedButtonColors = SegmentedButtonDefaults.colors()
    .copy(
        activeContainerColor = FwTheme.colors.blues.primary,
        activeContentColor = FwTheme.colors.whites.primary,
        activeBorderColor = FwTheme.colors.blues.secondary,
        inactiveContainerColor = FwTheme.colors.whites.primary,
        inactiveContentColor = FwTheme.colors.blues.primary,
        inactiveBorderColor = FwTheme.colors.blues.secondary,
    )

@Preview
@Composable
private fun FwSegmentedButtonPreview() {
    val speeds: List<Speed> = listOf(
        Speed.KilometersPerHour,
        Speed.MetersPerSecond,
        Speed.MilesPerHour,
        Speed.Knots,
    )

    FwTheme {
        Column(
            modifier = Modifier
                .background(brush = selectDayBackground())
                .fillMaxWidth()
                .padding(
                    horizontal = FwTheme.dimens.standard16,
                    vertical = FwTheme.dimens.standard8,
                ),
        ) {
            SingleChoiceSegmentedButtonRow(
                modifier = Modifier.fillMaxWidth(),
            ) {
                speeds.forEachIndexed { index, unit ->
                    FwSegmentedButton(
                        index = index,
                        count = speeds.size,
                        text = unit.toString(),
                        isSelected = unit == Speed.MilesPerHour,
                        onClick = { },
                    )
                }
            }
        }
    }
}
