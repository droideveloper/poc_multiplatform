package com.multiplatform.weather.city

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.multiplatform.weather.core.ui.FwTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun RowScope.CountryFlag(
    countryCode: CountryCode,
    modifier: Modifier = Modifier,
    countryFlagSize: CountryFlagSize = CountryFlagSize.Small,
) {
    Text(
        modifier = Modifier
            .padding(
                vertical = FwTheme.dimens.standard2,
                horizontal = FwTheme.dimens.standard6,
            )
            .then(modifier)
            .testTag("text_flag"),
        text = countryCode.selectFlag(),
        textAlign = TextAlign.Center,
        fontSize = countryFlagSize.sp,
    )
}

@Composable
internal fun CountryCode.selectFlag(): String = remember(value) {
    CountryFlagFactory.create(value)
}

sealed class CountryFlagSize(val sp: TextUnit) {

    data object Small : CountryFlagSize(sp = 12.sp)
    data object Medium : CountryFlagSize(sp = 16.sp)
    data object Large : CountryFlagSize(sp = 24.sp)
    data object Huge : CountryFlagSize(sp = 36.sp)
}

internal object CountryFlagFactory {
    private const val MinSupplementaryCodePoint = 0x10000

    private const val MinHighSurrogate = 0xD800
    private const val MinLowSurrogate = 0xDC00

    private const val AsciiOffset = 0x41
    private const val FlagOffset = 0x1F1E6

    private const val HighSurrogateEncodeOffset =
        (MinHighSurrogate - (MinSupplementaryCodePoint ushr 10))

    internal fun create(value: String): String {
        require(value.length == 2) { "$this is not valid country code" }
        val flag = value
            .map {
                val code = it.code.minus(AsciiOffset).plus(FlagOffset)
                val array = charArrayOf(highSurrogate(code), lowSurrogate(code))
                array.concatToString()
            }
        return flag.first() + flag.last()
    }

    private fun highSurrogate(codePoint: Int): Char {
        return ((codePoint ushr 10) + HighSurrogateEncodeOffset).toChar()
    }

    private fun lowSurrogate(codePoint: Int): Char {
        return ((codePoint and 0x3FF) + MinLowSurrogate).toChar()
    }
}

@Preview
@Composable
private fun CountryFlagPreview() {
    FwTheme {
        Row(
            modifier = Modifier
                .background(Color.White)
                .padding(FwTheme.dimens.standard8),
        ) {
            CountryFlag(
                countryCode = CountryCode.getOrThrow("TR"),
                countryFlagSize = CountryFlagSize.Large,
            )
        }
    }
}
