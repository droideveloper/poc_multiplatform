package com.multiplatform.weather.core.ui

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.multiplatform.td.core.ui.LocalTdDimens
import com.multiplatform.td.core.ui.TdColor
import com.multiplatform.td.core.ui.TdDimens
import com.multiplatform.td.core.ui.TdTheme

val LocalFwColors = staticCompositionLocalOf { fwColors }
val LocalFwTypography = staticCompositionLocalOf { fwTypographyProvider }

private val fwTypographyProvider = @Composable { fwTypography }

@Composable
fun FwTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalFwColors provides fwColors,
        LocalFwTypography provides { fwTypography },
    ) {
        TdTheme {
            MaterialTheme(
                colorScheme = lightColorScheme(
                    primary = FwTheme.colors.blues.primary,
                    background = FwTheme.colors.whites.secondary,
                ),
                shapes = Shapes(
                    small = RoundedCornerShape(FwTheme.dimens.standard72),
                ),
                content = content,
            )
        }
    }
}

object FwTheme {
    val colors: FwColors
        @Composable
        get() = LocalFwColors.current
    val dimens: TdDimens
        @Composable
        get() = LocalTdDimens.current
    val typography: FwTypography
        @Composable
        get() = LocalFwTypography.current()
}

@Immutable
data class FwColors(
    val whites: FwColor,
    val greys: FwColor,
    val blacks: FwColor,
    val blues: FwColor,
)

@Immutable
data class FwColor(
    val primary: Color,
    val secondary: Color,
    val light: Color,
)

@Immutable
data class FwTypography(
    val titlePrimary: TextStyle,
    val titleSecondary: TextStyle,
    val bodyPrimary: TextStyle,
    val bodySecondary: TextStyle,
    val spotPrimary: TextStyle,
    val spotSecondary: TextStyle,
)

internal fun TdColor.toFwColor() = FwColor(
    primary = primary,
    secondary = secondary,
    light = light,
)

private val fwTypography: FwTypography
    @Composable
    get() = FwTypography(
        titlePrimary = TextStyle.Default.copy(
            fontSize = 24.sp,
            color = FwTheme.colors.blacks.primary,
        ),
        titleSecondary = TextStyle.Default.copy(
            fontSize = 18.sp,
            color = FwTheme.colors.greys.primary,
        ),
        bodyPrimary = TextStyle.Default.copy(
            fontSize = 16.sp,
            color = FwTheme.colors.greys.primary,
        ),
        bodySecondary = TextStyle.Default.copy(
            fontSize = 14.sp,
            color = FwTheme.colors.greys.secondary,
        ),
        spotPrimary = TextStyle.Default.copy(
            fontSize = 12.sp,
            color = FwTheme.colors.greys.secondary,
        ),
        spotSecondary = TextStyle.Default.copy(
            fontSize = 10.sp,
            color = FwTheme.colors.blacks.primary,
        ),
    )

private val fwColors: FwColors = FwColors(
    whites = FwColor(
        primary = Color(0xFFFFFFFF),
        secondary = Color(0xFFFEFEFE),
        light = Color(0xFFF0F0F0),
    ),
    greys = FwColor(
        primary = Color(0xFF3D3F45),
        secondary = Color(0xFF676970),
        light = Color(0xFF8B8B8F),
    ),
    blacks = FwColor(
        primary = Color(0xFF020305),
        secondary = Color(0xFF191A1C),
        light = Color(0xFF3F4042),
    ),
    blues = FwColor(
        primary = Color(0xFF171D32),
        secondary = Color(0xFF243059),
        light = Color(0xFF87CEFA),
    ),
)
