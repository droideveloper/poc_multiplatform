package com.multiplatform.weather.forecast

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import com.multiplatform.td.core.ui.button.TdTextLinkBlue
import com.multiplatform.td.core.ui.card.TdCard
import com.multiplatform.td.core.ui.extensions.ignoreHorizontalPadding
import com.multiplatform.weather.core.ui.FwTheme
import com.multiplatform.weather.city.City
import com.multiplatform.weather.city.CityView
import com.multiplatform.weather.core.measure.Speed
import com.multiplatform.weather.core.measure.Temperature
import com.multiplatform.weather.core.measure.TemperatureAmount
import com.multiplatform.weather.core.measure.WindAmount
import com.multiplatform.weather.core.ui.FwImage
import com.multiplatform.weather.core.ui.selectDayBackground
import com.multiplatform.weather.forecast.today.SelectedCityState
import com.multiplatform.weather.forecast.today.WeatherData
import com.multiplatform.weather.forecast.today.WeatherDescriptionState
import com.multiplatform.weather.forecast.today.WeatherDetailDescriptionState
import com.multiplatform.weather.forecast.today.WeatherHourlyDescriptionState
import com.multiplatform.weather.forecast.today.WeatherNextDayDescriptionState
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import tdmultiplatform.weather.forecast.ui.generated.resources.Res
import tdmultiplatform.weather.forecast.ui.generated.resources.forecast_ui_humidity
import tdmultiplatform.weather.forecast.ui.generated.resources.forecast_ui_number_of_days
import tdmultiplatform.weather.forecast.ui.generated.resources.forecast_ui_pressure
import tdmultiplatform.weather.forecast.ui.generated.resources.forecast_ui_sunrise
import tdmultiplatform.weather.forecast.ui.generated.resources.forecast_ui_sunset
import tdmultiplatform.weather.forecast.ui.generated.resources.forecast_ui_temperature_felt
import tdmultiplatform.weather.forecast.ui.generated.resources.forecast_ui_today
import tdmultiplatform.weather.forecast.ui.generated.resources.forecast_ui_wind
import tdmultiplatform.weather.forecast.ui.generated.resources.ic_apparent_temperature
import tdmultiplatform.weather.forecast.ui.generated.resources.ic_humidity
import tdmultiplatform.weather.forecast.ui.generated.resources.ic_pin
import tdmultiplatform.weather.forecast.ui.generated.resources.ic_pressure
import tdmultiplatform.weather.forecast.ui.generated.resources.ic_settings
import tdmultiplatform.weather.forecast.ui.generated.resources.ic_sunrise
import tdmultiplatform.weather.forecast.ui.generated.resources.ic_sunset
import tdmultiplatform.weather.forecast.ui.generated.resources.ic_wind

@Composable
internal fun WeatherNextDayDescriptionItem(
    modifier: Modifier = Modifier,
    state: WeatherNextDayDescriptionState,
    unit: Temperature,
    currentDateTime: LocalDateTime,
    onClick: (LocalDate) -> Unit = {},
) {
    TdCard(
        modifier = Modifier
            .padding(FwTheme.dimens.standard4)
            .clickable { onClick(state.date) }
            .then(modifier),
        shape = RoundedCornerShape(FwTheme.dimens.standard4),
        containerColor = FwTheme.colors.whites.secondary,
    ) {
        Row(
            modifier = Modifier
                .padding(
                    horizontal = FwTheme.dimens.standard16,
                    vertical = FwTheme.dimens.standard12,
                )
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val (weatherDescriptionRes, iconRes) = selectWeatherDescription(state.weatherCode)
            Icon(
                modifier = Modifier
                    .size(FwTheme.dimens.standard36)
                    .background(
                        color = FwTheme.colors.whites.light.copy(
                            alpha = 0.5f,
                        ),
                        shape = RoundedCornerShape(FwTheme.dimens.standard16),
                    )
                    .padding(FwTheme.dimens.standard6),
                painter = rememberVectorPainter(vectorResource(iconRes)),
                contentDescription = stringResource(weatherDescriptionRes),
                tint = Color.Unspecified,
            )
            Spacer(modifier = Modifier.width(FwTheme.dimens.standard8))
            Text(
                text = state.selectTitle(currentDateTime),
                style = FwTheme.typography.bodySecondary.copy(
                    color = FwTheme.colors.greys.secondary,
                    fontWeight = FontWeight.Bold,
                ),
            )
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(weatherDescriptionRes),
                textAlign = TextAlign.Center,
                style = FwTheme.typography.spotPrimary.copy(
                    color = FwTheme.colors.greys.secondary,
                ),
            )
            Text(
                text = selectTemperature(state.temperature, unit),
                style = FwTheme.typography.titleSecondary.copy(
                    color = FwTheme.colors.greys.secondary,
                    fontWeight = FontWeight.Bold,
                ),
            )
        }
    }
}

@Composable
internal fun WeatherHourlyDescription(
    modifier: Modifier = Modifier,
    state: WeatherHourlyDescriptionState,
    days: Int,
    unit: Temperature,
    onClickNextDays: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = FwTheme.dimens.standard16)
            .then(modifier)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(Res.string.forecast_ui_today),
                style = FwTheme.typography.titleSecondary.copy(
                    color = FwTheme.colors.greys.secondary,
                    fontWeight = FontWeight.Bold,
                ),
            )
            TdTextLinkBlue(
                text = stringResource(Res.string.forecast_ui_number_of_days, days),
                textStyle = FwTheme.typography.bodySecondary.copy(
                    fontWeight = FontWeight.Bold,
                ),
                textColor = FwTheme.colors.blues.secondary,
                pressedTextColor = FwTheme.colors.blues.primary,
                onClick = onClickNextDays,
            )
        }
        Spacer(modifier = Modifier.height(FwTheme.dimens.standard12))
        val scrollState = rememberLazyListState()
        LazyRow(
            modifier = Modifier
                .ignoreHorizontalPadding(FwTheme.dimens.standard16)
                .fillMaxWidth(),
            state = scrollState,
            horizontalArrangement = Arrangement.spacedBy(
                space = FwTheme.dimens.standard8,
            ),
        ) {
            items(
                items = state.hourlyWeather,
                key = { it.time.toMillisecondOfDay() },
            ) { item ->
                WeatherHourlyDescriptionItem(
                    currentTime = state.time,
                    weather = item,
                    unit = unit,
                )
            }
        }
        LaunchedEffect(state.selectedPosition) {
            scrollState.animateScrollToItem(
                index = state.selectedPosition,
            )
        }
    }
}

@Composable
internal fun WeatherDetailDescription(
    modifier: Modifier = Modifier,
    state: WeatherDetailDescriptionState,
    temperatureUnit: Temperature,
    windUnit: Speed,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        verticalArrangement = Arrangement.Center,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            WeatherDetailDescriptionItemReversed(
                title = stringResource(Res.string.forecast_ui_temperature_felt),
                text = selectTemperature(state.temperature, temperatureUnit),
                icon = vectorResource(Res.drawable.ic_apparent_temperature),
            )
            Spacer(modifier = Modifier.weight(1f))
            WeatherDetailDescriptionItem(
                title = stringResource(Res.string.forecast_ui_wind),
                text = selectWind(state.wind, windUnit),
                icon = vectorResource(Res.drawable.ic_wind),
            )
        }
        Spacer(modifier = Modifier.height(FwTheme.dimens.standard16))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            WeatherDetailDescriptionItemReversed(
                title = stringResource(Res.string.forecast_ui_sunrise),
                text = selectTime(state.sunrise),
                icon = vectorResource(Res.drawable.ic_sunrise),
            )
            Spacer(modifier = Modifier.weight(1f))
            WeatherDetailDescriptionItem(
                title = stringResource(Res.string.forecast_ui_sunset),
                text = selectTime(state.sunset),
                icon = vectorResource(Res.drawable.ic_sunset),
            )
        }
        Spacer(modifier = Modifier.height(FwTheme.dimens.standard16))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            WeatherDetailDescriptionItemReversed(
                title = stringResource(Res.string.forecast_ui_humidity),
                text = selectHumidity(state.humidity),
                icon = vectorResource(Res.drawable.ic_humidity),
            )
            Spacer(modifier = Modifier.weight(1f))
            WeatherDetailDescriptionItem(
                title = stringResource(Res.string.forecast_ui_pressure),
                text = selectPressure(state.pressure),
                icon = vectorResource(Res.drawable.ic_pressure),
            )
        }
        Spacer(modifier = Modifier.height(FwTheme.dimens.standard16))
    }
}

@Composable
internal fun WeatherDescription(
    modifier: Modifier = Modifier,
    state: WeatherDescriptionState,
    unit: Temperature,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val (descriptionRes, weatherIconRes) = selectWeatherDescription(state.weatherCode)
        FwImage(
            modifier = Modifier.width(FwTheme.dimens.standard148),
            resource = vectorResource(weatherIconRes),
            contentDescription = null,
        )
        Spacer(modifier = Modifier.width(FwTheme.dimens.standard16))
        Column(
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = selectTemperature(state.temperature, unit),
                style = FwTheme.typography.titlePrimary.copy(
                    fontSize = 54.sp,
                    fontWeight = FontWeight.Bold,
                ),
            )
            Spacer(modifier = Modifier.height(FwTheme.dimens.standard6))
            Text(
                modifier = Modifier.sizeIn(maxWidth = FwTheme.dimens.standard148),
                text = stringResource(descriptionRes),
                style = FwTheme.typography.bodySecondary.copy(
                    fontWeight = FontWeight.Medium,
                )
            )
        }
    }
}

@Composable
internal fun SelectedCityToolbar(
    modifier: Modifier = Modifier,
    state: SelectedCityState,
    onPrimaryClicked: () -> Unit = {},
    onCityChangeClicked: (City) -> Unit = {},
) {
    TdCard(
        modifier = Modifier.padding(all = FwTheme.dimens.standard0)
            .then(modifier),
        shape = RoundedCornerShape(FwTheme.dimens.standard4),
        containerColor = FwTheme.colors.whites.secondary,
    ) {
        Row(
            modifier = Modifier.fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val selectedCities = remember { state.selectedCities }
            SelectedCityTitle(
                modifier = Modifier.fillMaxHeight(),
                city = state.city,
                selectedCities = state.selectedCities,
                isSelectCityEnabled = selectedCities.size > 1,
                onCityChangeClicked = onCityChangeClicked,
            )
            IconButton(
                modifier = Modifier.padding(
                    FwTheme.dimens.standard4,
                ),
                onClick = onPrimaryClicked,
            ) {
                Icon(
                    modifier = Modifier.size(FwTheme.dimens.standard24),
                    painter = rememberVectorPainter(vectorResource(Res.drawable.ic_settings)),
                    tint = FwTheme.colors.greys.secondary,
                    contentDescription = null,
                )
            }
        }
    }
}

@Composable
internal fun RowScope.SelectedCityTitle(
    modifier: Modifier = Modifier,
    city: City,
    selectedCities: List<City>,
    isSelectCityEnabled: Boolean,
    onCityChangeClicked: (City) -> Unit,
) {
    var cityDialogVisible by remember { mutableStateOf(false) }
    val onCityClick: (City) -> Unit = remember {
        { city ->
            cityDialogVisible = false
            onCityChangeClicked(city)
        }
    }
    val onDismissClick: () -> Unit = remember { { cityDialogVisible = false } }
    Row(
        modifier = Modifier
            .weight(1f)
            .then(modifier)
            .clickable {
                if (isSelectCityEnabled) { cityDialogVisible = true }
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier
                .padding(start = FwTheme.dimens.standard12)
                .size(FwTheme.dimens.standard24),
            painter = rememberVectorPainter(vectorResource(Res.drawable.ic_pin)),
            contentDescription = null,
            tint = FwTheme.colors.greys.secondary,
        )
        Spacer(modifier = Modifier.width(FwTheme.dimens.standard4))
        Text(
            text = "${city.name}, ${city.country.name}",
            style = FwTheme.typography.bodySecondary.copy(
                fontWeight = FontWeight.Bold
            ),
        )
        if (isSelectCityEnabled) {
            Spacer(modifier = Modifier.width(FwTheme.dimens.standard8))
            Icon(
                modifier = Modifier
                    .padding(start = FwTheme.dimens.standard8)
                    .size(FwTheme.dimens.standard24),
                painter = rememberVectorPainter(Icons.Sharp.ArrowDropDown),
                tint = FwTheme.colors.greys.secondary,
                contentDescription = null,
            )
            if (cityDialogVisible) {
                DropDownMenu(onDismissClick, selectedCities, onCityClick)
            }
        }
    }
}

@Composable
private fun DropDownMenu(
    onDismissClicked: () -> Unit,
    selectedCities: List<City>,
    onCityClick: (suggestion: City) -> Unit,
) {
    DropdownMenu(
        properties = PopupProperties(
            focusable = false,
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        ),
        modifier = Modifier
            .widthIn(max = FwTheme.dimens.standard256)
            .heightIn(max = FwTheme.dimens.standard256)
            .background(FwTheme.colors.whites.secondary)
            .testTag("city_picker"),
        expanded = true,
        onDismissRequest = onDismissClicked,
    ) {
        selectedCities.forEach {
            DropdownMenuItem(
                onClick = { onCityClick(it) },
                text = { CityView(it, onClick = onCityClick) },
            )
        }
    }
}

@Composable
internal fun WeatherDetailDescriptionItem(
    modifier: Modifier = Modifier.sizeIn(minWidth = FwTheme.dimens.standard108),
    title: String,
    text: String,
    icon: ImageVector,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(FwTheme.dimens.standard36)
                .background(
                    color = FwTheme.colors.whites.light.copy(
                        alpha = 0.5f,
                    ),
                    shape = RoundedCornerShape(FwTheme.dimens.standard8),
                ),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                modifier = Modifier.size(FwTheme.dimens.standard24),
                painter = rememberVectorPainter(icon),
                contentDescription = null,
                tint = FwTheme.colors.greys.secondary,
            )
        }
        Spacer(modifier = Modifier.width(FwTheme.dimens.standard12))
        Column(
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = title,
                style = FwTheme.typography.spotPrimary.copy(
                    fontWeight = FontWeight.Medium,
                )
            )
            Spacer(modifier = Modifier.height(FwTheme.dimens.standard2))
            Text(
                text = text,
                style = FwTheme.typography.bodySecondary.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = FwTheme.colors.blacks.secondary,
                )
            )
        }
    }
}

@Composable
internal fun WeatherDetailDescriptionItemReversed(
    modifier: Modifier = Modifier.sizeIn(minWidth = FwTheme.dimens.standard108),
    title: String,
    text: String,
    icon: ImageVector,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = title,
                style = FwTheme.typography.spotPrimary.copy(
                    fontWeight = FontWeight.Medium,
                )
            )
            Spacer(modifier = Modifier.height(FwTheme.dimens.standard2))
            Text(
                text = text,
                style = FwTheme.typography.bodySecondary.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = FwTheme.colors.blacks.secondary,
                )
            )
        }
        Spacer(modifier = Modifier.width(FwTheme.dimens.standard12))
        Box(
            modifier = Modifier
                .size(FwTheme.dimens.standard36)
                .background(
                    color = FwTheme.colors.whites.light.copy(
                        alpha = 0.5f,
                    ),
                    shape = RoundedCornerShape(FwTheme.dimens.standard8),
                ),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                modifier = Modifier.size(FwTheme.dimens.standard24),
                painter = rememberVectorPainter(icon),
                contentDescription = null,
                tint = FwTheme.colors.greys.secondary,
            )
        }
    }
}

@Composable
internal fun WeatherHourlyDescriptionItem(
    modifier: Modifier = Modifier,
    currentTime: LocalTime,
    weather: WeatherData,
    unit: Temperature,
) {
    // same for this
    val isSelected = remember(currentTime) { currentTime.hour == weather.time.hour  }
    // should be in selectors
    val backgroundModifier = when {
        isSelected -> Modifier.background(
            color = FwTheme.colors.blues.light.copy(
                alpha = 0.5f,
            ),
            shape = RoundedCornerShape(FwTheme.dimens.standard48)
        )
        else -> Modifier
    }
    Column(
        modifier = Modifier
            .then(modifier)
            .then(backgroundModifier)
            .padding(
                horizontal = FwTheme.dimens.standard8,
                vertical = FwTheme.dimens.standard12,
            ),
        verticalArrangement = Arrangement.spacedBy(
            space = FwTheme.dimens.standard2,
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val (weatherDescriptionRes, iconRes) = selectWeatherDescription(weather.code)
        Icon(
            modifier = Modifier
                .size(FwTheme.dimens.standard36)
                .background(
                    color = FwTheme.colors.whites.light.copy(
                        alpha = 0.5f,
                    ),
                    shape = RoundedCornerShape(FwTheme.dimens.standard16),
                )
                .padding(FwTheme.dimens.standard6),
            painter = rememberVectorPainter(vectorResource(iconRes)),
            contentDescription = stringResource(weatherDescriptionRes),
            tint = Color.Unspecified,
        )
        Spacer(modifier = Modifier.height(FwTheme.dimens.standard8))
        Text(
            text = selectMarkedTime(weather.time),
            style = FwTheme.typography.spotPrimary.copy(
                fontWeight = FontWeight.Medium,
            )
        )
        Spacer(modifier = Modifier.height(FwTheme.dimens.standard2))
        Text(
            text = selectTemperature(weather.temperature, unit),
            style = FwTheme.typography.bodySecondary.copy(
                fontWeight = FontWeight.SemiBold,
                color = FwTheme.colors.blacks.secondary,
            ),
        )
        Spacer(modifier = Modifier.height(FwTheme.dimens.standard2))
    }
}

@Preview
@Composable
private fun WeatherDescriptionPreview() {
    FwTheme {
        Column(
            modifier = Modifier
                .background(brush = selectDayBackground())
                .fillMaxWidth()
                .padding(
                    vertical = FwTheme.dimens.standard8,
                    horizontal = FwTheme.dimens.standard16,
                )
        ) {
            WeatherDescription(
                state = WeatherDescriptionState(
                    temperature = TemperatureAmount(
                        amount = 32.0,
                        unit = Temperature.Celsius,
                    ),
                    weatherCode = WeatherCode.getOrThrow(22),
                ),
                unit = Temperature.Celsius,
            )
        }
    }
}

@Preview
@Composable
private fun WeatherNextDayDescriptionItemPreview() {
    FwTheme {
        Column(
            modifier = Modifier
                .background(brush = selectDayBackground())
                .fillMaxWidth()
                .padding(
                    vertical = FwTheme.dimens.standard8,
                    horizontal = FwTheme.dimens.standard16,
                )
        ) {
            WeatherNextDayDescriptionItem(
                currentDateTime = LocalDateTime.Companion.parse("2025-07-29T10:00:00"),
                state = WeatherNextDayDescriptionState(
                    temperature = TemperatureAmount(
                        amount = 32.0,
                        unit = Temperature.Celsius,
                    ),
                    weatherCode = WeatherCode.getOrThrow(22),
                    date = LocalDateTime.Companion.parse("2025-07-30T10:00:00").date
                ),
                unit = Temperature.Celsius,
            )
        }
    }
}

@Preview
@Composable
private fun WeatherDescriptionItemPreview() {
    val wind = WindAmount(
        amount = 13.0,
        unit = Speed.KilometersPerHour,
    )

    FwTheme {
        Column(
            modifier = Modifier
                .background(brush = selectDayBackground())
                .padding(FwTheme.dimens.standard8)
        ) {
            WeatherDetailDescriptionItem(
                title = stringResource(Res.string.forecast_ui_wind),
                text = selectWind(wind, Speed.KilometersPerHour),
                icon = vectorResource(Res.drawable.ic_wind),
            )
        }
    }
}

@Preview
@Composable
private fun WeatherDescriptionItemReversedPreview() {
    val temperature = TemperatureAmount(
        amount = 32.0,
        unit = Temperature.Celsius,
    )

    FwTheme {
        Column(
            modifier = Modifier
                .background(brush = selectDayBackground())
                .padding(FwTheme.dimens.standard8)
        ) {
            WeatherDetailDescriptionItemReversed(
                title = stringResource(Res.string.forecast_ui_temperature_felt),
                text = selectTemperature(temperature, Temperature.Celsius),
                icon = vectorResource(Res.drawable.ic_apparent_temperature),
            )
        }
    }
}

@Preview
@Composable
private fun WeatherHourlyDescriptionItemPreview() {
    FwTheme {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(FwTheme.dimens.standard8)
        ) {
            WeatherHourlyDescriptionItem(
                currentTime = LocalTime.fromSecondOfDay(3600),
                weather = WeatherData(
                    time = LocalTime.fromSecondOfDay(3600),
                    temperature = TemperatureAmount(
                        amount = 32.0,
                        unit = Temperature.Celsius,
                    ),
                    code = WeatherCode.getOrThrow(92),
                ),
                unit = Temperature.Celsius,
            )
        }
    }
}
