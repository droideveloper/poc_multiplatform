package com.multiplatform.weather.city

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.multiplatform.weather.core.ui.FwTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CityView(
    city: City,
    onClick: (City) -> Unit = {},
    icon: @Composable () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(city) },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CountryFlag(
            countryCode = city.country.code,
            countryFlagSize = CountryFlagSize.Medium,
        )
        Spacer(modifier = Modifier.width(FwTheme.dimens.standard4))
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(end = FwTheme.dimens.standard8),
            text = selectCity(city),
            style = FwTheme.typography.bodySecondary.copy(
                color = FwTheme.colors.blues.secondary
            ),
        )
        Spacer(modifier = Modifier.width(FwTheme.dimens.standard4))
        icon()
    }
}

@Composable
internal fun selectCity(city: City): String = remember(city) {
    "${city.name}, ${city.country.code.value.uppercase()}"
}

@Preview
@Composable
private fun CityViewPreview() {
    FwTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
        ) {
            CityView(city = city)
        }
    }
}
