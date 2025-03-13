package com.multiplatform.todo.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.multiplatform.td.core.ui.TdTheme

@Immutable
data class ColorPalette(
    val color: Color,
    val backgroundColor: Color,
)

sealed class ColorPaletteException(
    override val message: String? = null
) : IllegalArgumentException(message) {

    data class Invalid(val color: Long) : ColorPaletteException(
        message = "invalid color = $color"
    )
}

@Composable
fun selectBackgroundColor(color: Color): Color {
    val palette: ColorPalette? = calendarTaskPalette.firstOrNull { palette ->
        palette.color == color
    }
    return when (palette) {
        null -> throw ColorPaletteException.Invalid(color.value.toLong())
        else -> palette.backgroundColor
    }
}

private const val BackgroundAlpha = 0.4f

private val calendarTaskPalette: List<ColorPalette>
    @Composable get() = listOf(
        ColorPalette(
            color = TdTheme.colors.reds.primary,
            backgroundColor = TdTheme.colors.reds.light.copy(
                alpha = BackgroundAlpha
            )
        ),
        ColorPalette(
            color = TdTheme.colors.pinks.primary,
            backgroundColor = TdTheme.colors.pinks.light.copy(
                alpha = BackgroundAlpha
            )
        ),
        ColorPalette(
            color = TdTheme.colors.purples.primary,
            backgroundColor = TdTheme.colors.purples.light.copy(
                alpha = BackgroundAlpha
            )
        ),
        ColorPalette(
            color = TdTheme.colors.deepPurples.primary,
            backgroundColor = TdTheme.colors.deepPurples.light.copy(
                alpha = BackgroundAlpha
            )
        ),
        ColorPalette(
            color = TdTheme.colors.indigos.primary,
            backgroundColor = TdTheme.colors.indigos.light.copy(
                alpha = BackgroundAlpha
            )
        ),
        ColorPalette(
            color = TdTheme.colors.blues.primary,
            backgroundColor = TdTheme.colors.blues.light.copy(
                alpha = BackgroundAlpha
            )
        ),
        ColorPalette(
            color = TdTheme.colors.lightBlues.primary,
            backgroundColor = TdTheme.colors.lightBlues.light.copy(
                alpha = BackgroundAlpha
            )
        ),
        ColorPalette(
            color = TdTheme.colors.cyans.primary,
            backgroundColor = TdTheme.colors.cyans.light.copy(
                alpha = BackgroundAlpha
            )
        ),
        ColorPalette(
            color = TdTheme.colors.teals.primary,
            backgroundColor = TdTheme.colors.teals.light.copy(
                alpha = BackgroundAlpha
            )
        ),
        ColorPalette(
            color = TdTheme.colors.greens.primary,
            backgroundColor = TdTheme.colors.greens.light.copy(
                alpha = BackgroundAlpha
            )
        ),
        ColorPalette(
            color = TdTheme.colors.lightGreens.primary,
            backgroundColor = TdTheme.colors.lightGreens.light.copy(
                alpha = BackgroundAlpha
            )
        ),
        ColorPalette(
            color = TdTheme.colors.limes.primary,
            backgroundColor = TdTheme.colors.limes.light.copy(
                alpha = BackgroundAlpha
            )
        ),
        ColorPalette(
            color = TdTheme.colors.yellows.primary,
            backgroundColor = TdTheme.colors.yellows.light.copy(
                alpha = BackgroundAlpha
            )
        ),
        ColorPalette(
            color = TdTheme.colors.ambers.primary,
            backgroundColor = TdTheme.colors.ambers.light.copy(
                alpha = BackgroundAlpha
            )
        ),
        ColorPalette(
            color = TdTheme.colors.oranges.primary,
            backgroundColor = TdTheme.colors.oranges.light.copy(
                alpha = BackgroundAlpha
            )
        ),
        ColorPalette(
            color = TdTheme.colors.deepOranges.primary,
            backgroundColor = TdTheme.colors.deepOranges.light.copy(
                alpha = BackgroundAlpha
            )
        ),
        ColorPalette(
            color = TdTheme.colors.browns.primary,
            backgroundColor = TdTheme.colors.browns.light.copy(
                alpha = BackgroundAlpha
            )
        ),
        ColorPalette(
            color = TdTheme.colors.blueGreys.primary,
            backgroundColor = TdTheme.colors.blueGreys.light.copy(
                alpha = BackgroundAlpha
            )
        ),
    )