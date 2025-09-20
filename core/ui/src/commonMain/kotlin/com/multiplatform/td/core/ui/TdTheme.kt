package com.multiplatform.td.core.ui

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val LocalTdColors = staticCompositionLocalOf { tdColors }
val LocalTdDimens = staticCompositionLocalOf { tdDimens }
val LocalTdTypography = staticCompositionLocalOf { tdTypographyProvider }

private val tdTypographyProvider = @Composable { tdTypography }

@Composable
fun TdTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalTdColors provides tdColors,
        LocalTdDimens provides tdDimens,
        LocalTdTypography provides { tdTypography },
    ) {
        MaterialTheme(
            colorScheme = lightColorScheme(
                primary = TdTheme.colors.oranges.primary,
                background = TdTheme.colors.whites.primary,
            ),
            shapes = Shapes(
                small = RoundedCornerShape(TdTheme.dimens.standard72),
            ),
            content = content,
        )
    }
}

object TdTheme {
    val colors: TdColors
        @Composable
        get() = LocalTdColors.current
    val dimens: TdDimens
        @Composable
        get() = LocalTdDimens.current
    val typography: TdTypography
        @Composable
        get() = LocalTdTypography.current()
}

@Immutable
data class TdDimens(
    val standard0: Dp,
    val standard1: Dp,
    val standard2: Dp,
    val standard4: Dp,
    val standard6: Dp,
    val standard8: Dp,
    val standard10: Dp,
    val standard12: Dp,
    val standard16: Dp,
    val standard18: Dp,
    val standard20: Dp,
    val standard24: Dp,
    val standard28: Dp,
    val standard30: Dp,
    val standard32: Dp,
    val standard36: Dp,
    val standard40: Dp,
    val standard42: Dp,
    val standard48: Dp,
    val standard54: Dp,
    val standard60: Dp,
    val standard64: Dp,
    val standard72: Dp,
    val standard96: Dp,
    val standard108: Dp,
    val standard128: Dp,
    val standard148: Dp,
    val standard256: Dp,
)

val tdDimens = TdDimens(
    standard0 = 0.dp,
    standard1 = 1.dp,
    standard2 = 2.dp,
    standard4 = 4.dp,
    standard6 = 6.dp,
    standard8 = 8.dp,
    standard10 = 10.dp,
    standard12 = 12.dp,
    standard16 = 16.dp,
    standard18 = 18.dp,
    standard20 = 20.dp,
    standard24 = 24.dp,
    standard28 = 28.dp,
    standard30 = 30.dp,
    standard32 = 32.dp,
    standard36 = 36.dp,
    standard40 = 40.dp,
    standard42 = 42.dp,
    standard48 = 48.dp,
    standard54 = 54.dp,
    standard60 = 60.dp,
    standard64 = 64.dp,
    standard72 = 72.dp,
    standard96 = 96.dp,
    standard108 = 108.dp,
    standard128 = 128.dp,
    standard148 = 148.dp,
    standard256 = 256.dp,
)

@Immutable
data class TdColor(
    val primary: Color,
    val secondary: Color,
    val light: Color,
)

@Immutable
data class TdColors(
    val reds: TdColor,
    val pinks: TdColor,
    val purples: TdColor,
    val deepPurples: TdColor,
    val indigos: TdColor,
    val blues: TdColor,
    val lightBlues: TdColor,
    val cyans: TdColor,
    val teals: TdColor,
    val greens: TdColor,
    val lightGreens: TdColor,
    val limes: TdColor,
    val yellows: TdColor,
    val ambers: TdColor,
    val oranges: TdColor,
    val deepOranges: TdColor,
    val browns: TdColor,
    val greys: TdColor,
    val blueGreys: TdColor,
    val blacks: TdColor,
    val whites: TdColor,
)

private val tdColors = TdColors(
    reds = TdColor(
        primary = Color(0xFFF44336),
        secondary = Color(0xFFEF5350),
        light = Color(0xFFE57373),
    ),
    pinks = TdColor(
        primary = Color(0xFFE91E63),
        secondary = Color(0xFFEC407A),
        light = Color(0xFFF06292),
    ),
    purples = TdColor(
        primary = Color(0xFF9C27B0),
        secondary = Color(0xFFAB47BC),
        light = Color(0xFFBA68C8),
    ),
    deepPurples = TdColor(
        primary = Color(0xFF673AB7),
        secondary = Color(0xFF7E57C2),
        light = Color(0xFF9575CD),
    ),
    indigos = TdColor(
        primary = Color(0xFF3F51B5),
        secondary = Color(0xFF5C6BC0),
        light = Color(0xFF7986CB),
    ),
    blues = TdColor(
        primary = Color(0xFF2196F3),
        secondary = Color(0xFF42A5F5),
        light = Color(0xFF64B5F6),
    ),
    lightBlues = TdColor(
        primary = Color(0xFF03A9F4),
        secondary = Color(0xFF29B6F6),
        light = Color(0xFF4FC3F7),
    ),
    cyans = TdColor(
        primary = Color(0xFF00BCD4),
        secondary = Color(0xFF26C6DA),
        light = Color(0xFF4DD0E1),
    ),
    teals = TdColor(
        primary = Color(0xFF009688),
        secondary = Color(0xFF26A69A),
        light = Color(0xFF4DB6AC),
    ),
    greens = TdColor(
        primary = Color(0xFF4CAF50),
        secondary = Color(0xFF66BB6A),
        light = Color(0xFF81C784),
    ),
    lightGreens = TdColor(
        primary = Color(0xFF8BC34A),
        secondary = Color(0xFF9CCC65),
        light = Color(0xFFAED581),
    ),
    limes = TdColor(
        primary = Color(0xFFCDDC39),
        secondary = Color(0xFFD4E157),
        light = Color(0xFFDCE775),
    ),
    yellows = TdColor(
        primary = Color(0xFFFFEB3B),
        secondary = Color(0xFFFFEE58),
        light = Color(0xFFFFF176),
    ),
    ambers = TdColor(
        primary = Color(0xFFFFC107),
        secondary = Color(0xFFFFCA28),
        light = Color(0xFFFFD54F),
    ),
    oranges = TdColor(
        primary = Color(0xFFFF9800),
        secondary = Color(0xFFFFA726),
        light = Color(0xFFFFB74D),
    ),
    deepOranges = TdColor(
        primary = Color(0xFFFF5722),
        secondary = Color(0xFFFF7043),
        light = Color(0xFFFF8A65),
    ),
    browns = TdColor(
        primary = Color(0xFF795548),
        secondary = Color(0xFF8D6E63),
        light = Color(0xFFA1887F),
    ),
    greys = TdColor(
        primary = Color(0xFF9E9E9E),
        secondary = Color(0xFFBDBDBD),
        light = Color(0xFFE0E0E0),
    ),
    blueGreys = TdColor(
        primary = Color(0xFF607D8B),
        secondary = Color(0xFF78909C),
        light = Color(0xFF90A4AE),
    ),
    blacks = TdColor(
        primary = Color(0xFF1E1F26),
        secondary = Color(0xEE3F4050),
        light = Color(0xCC3F4050),
    ),
    whites = TdColor(
        primary = Color(0xFFFFFFFF),
        secondary = Color(0xFFF7F7FA),
        light = Color(0xFFF2F2F5),
    ),
)

@Immutable
data class TdTypography(
    val titleHero: TextStyle,
    val titleExtraLarge: TextStyle,
    val titleLarge: TextStyle,
    val titleBig: TextStyle,
    val titleMedium: TextStyle,
    val titleSmall: TextStyle,
    val titleSmallSemi: TextStyle,
    val titleTiny: TextStyle,
    val titleTinySemi: TextStyle,
    val linkSmall: TextStyle,
    val linkTiny: TextStyle,
    val bodyBig: TextStyle,
    val bodyMedium: TextStyle,
    val bodySmall: TextStyle,
    val bodyTiny: TextStyle,
)

private val tdTypography
    @Composable
    get() = TdTypography(
        titleHero = TextStyle.Default.copy(
            fontSize = 28.sp,
            lineHeight = 36.sp,
            fontWeight = FontWeight.Bold,
            color = tdColors.blacks.primary,
        ),
        titleExtraLarge = TextStyle.Default.copy(
            fontSize = 24.sp,
            lineHeight = 32.sp,
            fontWeight = FontWeight.Bold,
            color = tdColors.blacks.primary,
        ),
        titleLarge = TextStyle.Default.copy(
            fontSize = 21.sp,
            lineHeight = 28.sp,
            fontWeight = FontWeight.Bold,
            color = tdColors.blacks.primary,
        ),
        titleBig = TextStyle.Default.copy(
            fontSize = 18.sp,
            lineHeight = 24.sp,
            fontWeight = FontWeight.Bold,
            color = tdColors.blacks.primary,
        ),
        titleMedium = TextStyle.Default.copy(
            fontSize = 16.sp,
            lineHeight = 24.sp,
            fontWeight = FontWeight.Bold,
            color = tdColors.blacks.primary,
        ),
        titleSmall = TextStyle.Default.copy(
            fontSize = 14.sp,
            lineHeight = 20.sp,
            fontWeight = FontWeight.Bold,
            color = tdColors.blacks.primary,
        ),
        titleSmallSemi = TextStyle.Default.copy(
            fontSize = 14.sp,
            lineHeight = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = tdColors.blacks.primary,
        ),
        titleTiny = TextStyle.Default.copy(
            fontSize = 12.sp,
            lineHeight = 16.sp,
            fontWeight = FontWeight.Bold,
            color = tdColors.blacks.primary,
        ),
        titleTinySemi = TextStyle.Default.copy(
            fontSize = 12.sp,
            lineHeight = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = tdColors.greys.light,
        ),
        bodyBig = TextStyle.Default.copy(
            fontSize = 18.sp,
            lineHeight = 24.sp,
            fontWeight = FontWeight.Normal,
            color = tdColors.greys.light,
        ),
        bodyMedium = TextStyle.Default.copy(
            fontSize = 16.sp,
            lineHeight = 24.sp,
            fontWeight = FontWeight.Normal,
            color = tdColors.greys.light,
        ),
        bodySmall = TextStyle.Default.copy(
            fontSize = 14.sp,
            lineHeight = 20.sp,
            fontWeight = FontWeight.Normal,
            color = tdColors.greys.light,
        ),
        linkSmall = TextStyle.Default.copy(
            fontSize = 14.sp,
            lineHeight = 20.sp,
            fontWeight = FontWeight.Bold,
            color = tdColors.blues.primary,
            textDecoration = TextDecoration.Underline,
        ),
        bodyTiny = TextStyle.Default.copy(
            fontSize = 12.sp,
            lineHeight = 16.sp,
            fontWeight = FontWeight.Normal,
            color = tdColors.greys.light,
        ),
        linkTiny = TextStyle.Default.copy(
            fontSize = 12.sp,
            lineHeight = 16.sp,
            fontWeight = FontWeight.Bold,
            color = tdColors.blues.primary,
            textDecoration = TextDecoration.Underline,
        ),
    )

fun Color?.default(defaultColor: Color): Color {
    return if (this == null || this == Color.Unspecified) {
        defaultColor
    } else {
        this
    }
}
