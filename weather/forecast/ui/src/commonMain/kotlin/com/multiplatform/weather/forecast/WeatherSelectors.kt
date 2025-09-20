package com.multiplatform.weather.forecast

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.multiplatform.weather.core.measure.HumidityAmount
import com.multiplatform.weather.core.measure.PressureAmount
import com.multiplatform.weather.core.measure.Speed
import com.multiplatform.weather.core.measure.Temperature
import com.multiplatform.weather.core.measure.TemperatureAmount
import com.multiplatform.weather.core.measure.UvIndexAmount
import com.multiplatform.weather.core.measure.WindAmount
import com.multiplatform.weather.core.measure.inject.MeasureComponent
import com.multiplatform.weather.core.measure.inject.create
import com.multiplatform.weather.core.measure.toCelsius
import com.multiplatform.weather.core.measure.toFahrenheit
import com.multiplatform.weather.core.measure.toKilometersPerHour
import com.multiplatform.weather.core.measure.toKnots
import com.multiplatform.weather.core.measure.toMetersPerSecond
import com.multiplatform.weather.core.measure.toMilesPerHour
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.format
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import tdmultiplatform.weather.forecast.ui.generated.resources.Res
import tdmultiplatform.weather.forecast.ui.generated.resources.forecast_ui_clear_sky
import tdmultiplatform.weather.forecast.ui.generated.resources.forecast_ui_dense_drizzle
import tdmultiplatform.weather.forecast.ui.generated.resources.forecast_ui_dense_freezing_drizzle
import tdmultiplatform.weather.forecast.ui.generated.resources.forecast_ui_depositing_rime_fog
import tdmultiplatform.weather.forecast.ui.generated.resources.forecast_ui_foggy
import tdmultiplatform.weather.forecast.ui.generated.resources.forecast_ui_heavy_freezing_rain
import tdmultiplatform.weather.forecast.ui.generated.resources.forecast_ui_heavy_rain
import tdmultiplatform.weather.forecast.ui.generated.resources.forecast_ui_heavy_snow_fall
import tdmultiplatform.weather.forecast.ui.generated.resources.forecast_ui_heavy_snow_showers
import tdmultiplatform.weather.forecast.ui.generated.resources.forecast_ui_light_drizzle
import tdmultiplatform.weather.forecast.ui.generated.resources.forecast_ui_mainly_clear
import tdmultiplatform.weather.forecast.ui.generated.resources.forecast_ui_moderate_drizzle
import tdmultiplatform.weather.forecast.ui.generated.resources.forecast_ui_moderate_rain_showers
import tdmultiplatform.weather.forecast.ui.generated.resources.forecast_ui_moderate_snow_fall
import tdmultiplatform.weather.forecast.ui.generated.resources.forecast_ui_moderate_thunderstorm
import tdmultiplatform.weather.forecast.ui.generated.resources.forecast_ui_overcast
import tdmultiplatform.weather.forecast.ui.generated.resources.forecast_ui_partly_cloudy
import tdmultiplatform.weather.forecast.ui.generated.resources.forecast_ui_rainy
import tdmultiplatform.weather.forecast.ui.generated.resources.forecast_ui_slight_freezing_drizzle
import tdmultiplatform.weather.forecast.ui.generated.resources.forecast_ui_slight_rain
import tdmultiplatform.weather.forecast.ui.generated.resources.forecast_ui_slight_rain_showers
import tdmultiplatform.weather.forecast.ui.generated.resources.forecast_ui_slight_snow_fall
import tdmultiplatform.weather.forecast.ui.generated.resources.forecast_ui_slight_snow_showers
import tdmultiplatform.weather.forecast.ui.generated.resources.forecast_ui_snow_grains
import tdmultiplatform.weather.forecast.ui.generated.resources.forecast_ui_thunderstorm_with_heavy_hail
import tdmultiplatform.weather.forecast.ui.generated.resources.forecast_ui_thunderstorm_with_slight_hail
import tdmultiplatform.weather.forecast.ui.generated.resources.forecast_ui_today
import tdmultiplatform.weather.forecast.ui.generated.resources.forecast_ui_violent_rain_showers
import tdmultiplatform.weather.forecast.ui.generated.resources.ic_cloudy
import tdmultiplatform.weather.forecast.ui.generated.resources.ic_heavysnow
import tdmultiplatform.weather.forecast.ui.generated.resources.ic_rainshower
import tdmultiplatform.weather.forecast.ui.generated.resources.ic_rainy
import tdmultiplatform.weather.forecast.ui.generated.resources.ic_rainythunder
import tdmultiplatform.weather.forecast.ui.generated.resources.ic_snowy
import tdmultiplatform.weather.forecast.ui.generated.resources.ic_snowyrainy
import tdmultiplatform.weather.forecast.ui.generated.resources.ic_sunny
import tdmultiplatform.weather.forecast.ui.generated.resources.ic_thunder
import tdmultiplatform.weather.forecast.ui.generated.resources.ic_very_cloudy

@Composable
internal fun rememberMeasureComponent(): MeasureComponent {
    return remember { MeasureComponent.create() }
}

@Composable
internal fun selectTemperature(
    temperature: TemperatureAmount,
    unit: Temperature,
): String {
    val component = rememberMeasureComponent()
    val temperatureFormatter = remember { component.temperatureAmountFormatter }
    val amount = when (unit) {
        Temperature.Fahrenheit -> temperature.toFahrenheit()
        Temperature.Celsius -> temperature.toCelsius()
    }
    return temperatureFormatter.format(amount)
}

@Composable
internal fun selectWind(
    wind: WindAmount,
    unit: Speed,
): String {
    val component = rememberMeasureComponent()
    val speedFormatter = remember { component.windAmountFormatter }
    val amount = when (unit) {
        Speed.Knots -> wind.toKnots()
        Speed.MilesPerHour -> wind.toMilesPerHour()
        Speed.MetersPerSecond -> wind.toMetersPerSecond()
        Speed.KilometersPerHour -> wind.toKilometersPerHour()
    }
    return speedFormatter.format(amount)
}

@Composable
internal fun selectUvIndex(uvIndex: UvIndexAmount): String {
    val component = rememberMeasureComponent()
    val uvIndexFormatter = remember { component.uvIndexAmountFormatter }
    return uvIndexFormatter.format(uvIndex)
}

@Composable
internal fun selectHumidity(humidity: HumidityAmount): String {
    val component = rememberMeasureComponent()
    val humidityFormatter = remember { component.humidityAmountFormatter }
    return humidityFormatter.format(humidity)
}

@Composable
internal fun selectPressure(pressure: PressureAmount): String {
    val component = rememberMeasureComponent()
    val pressureFormatter = remember { component.pressureAmountFormatter }
    return pressureFormatter.format(pressure)
}

@Composable
internal fun selectTime(time: LocalTime): String {
    val timeFormat = remember {
        LocalTime.Format {
            hour()
            char(':')
            minute()
        }
    }
    return time.format(format = timeFormat)
}

@Composable
internal fun selectCurrentDateTime(dateTime: LocalDateTime): String {
    val dateTimeFormat = remember {
        LocalDateTime.Format {
            day(Padding.NONE)
            char(' ')
            monthName(MonthNames.ENGLISH_ABBREVIATED)
            char(' ')
            amPmHour(Padding.NONE)
            char(':')
            minute()
            char(' ')
            amPmMarker("am", "pm")
        }
    }
    val today = stringResource(Res.string.forecast_ui_today)
    return "$today, ${dateTimeFormat.format(dateTime)}"
}

@Composable
internal fun selectMarkedTime(time: LocalTime): String {
    val timeFormat = remember {
        LocalTime.Format {
            amPmHour(Padding.NONE)
            char(' ')
            amPmMarker("AM", "PM")
        }
    }
    return timeFormat.format(time)
}

@Composable
internal fun selectLocalDate(date: LocalDate): AnnotatedString {
    val dayFormat = remember {
        LocalDate.Format {
            dayOfWeek(DayOfWeekNames.ENGLISH_FULL)
            char('\n')
        }
    }
    val dateFormat = remember {
        LocalDate.Format {
            day(Padding.NONE)
            char(' ')
            monthName(MonthNames.ENGLISH_ABBREVIATED)
        }
    }

    return buildAnnotatedString {
        append(dayFormat.format(date))
        withStyle(SpanStyle(fontSize = 10.sp)) {
            append(dateFormat.format(date))
        }
    }
}

@Composable
internal fun selectWeatherDescription(code: WeatherCode): WeatherDescription = remember(code) {
    val (stringRes, drawableRes) = resourceFor(code)
    WeatherDescription(stringRes, drawableRes)
}

private fun resourceFor(code: WeatherCode): Pair<StringResource, DrawableResource> = when (code.code) {
    0 -> Res.string.forecast_ui_clear_sky to Res.drawable.ic_sunny
    1 -> Res.string.forecast_ui_mainly_clear to Res.drawable.ic_cloudy
    2 -> Res.string.forecast_ui_partly_cloudy to Res.drawable.ic_cloudy
    3 -> Res.string.forecast_ui_overcast to Res.drawable.ic_cloudy
    45 -> Res.string.forecast_ui_foggy to Res.drawable.ic_very_cloudy
    48 -> Res.string.forecast_ui_depositing_rime_fog to Res.drawable.ic_very_cloudy
    51 -> Res.string.forecast_ui_light_drizzle to Res.drawable.ic_rainshower
    53 -> Res.string.forecast_ui_moderate_drizzle to Res.drawable.ic_rainshower
    55, 66 -> Res.string.forecast_ui_dense_drizzle to Res.drawable.ic_rainshower
    56 -> Res.string.forecast_ui_slight_freezing_drizzle to Res.drawable.ic_snowyrainy
    57 -> Res.string.forecast_ui_dense_freezing_drizzle to Res.drawable.ic_snowyrainy
    61 -> Res.string.forecast_ui_slight_rain to Res.drawable.ic_rainy
    63 -> Res.string.forecast_ui_rainy to Res.drawable.ic_rainy
    65 -> Res.string.forecast_ui_heavy_rain to Res.drawable.ic_rainy
    67 -> Res.string.forecast_ui_heavy_freezing_rain to Res.drawable.ic_snowyrainy
    71 -> Res.string.forecast_ui_slight_snow_fall to Res.drawable.ic_snowy
    73 -> Res.string.forecast_ui_moderate_snow_fall to Res.drawable.ic_heavysnow
    75 -> Res.string.forecast_ui_heavy_snow_fall to Res.drawable.ic_heavysnow
    77 -> Res.string.forecast_ui_snow_grains to Res.drawable.ic_heavysnow
    80 -> Res.string.forecast_ui_slight_rain_showers to Res.drawable.ic_rainshower
    81 -> Res.string.forecast_ui_moderate_rain_showers to Res.drawable.ic_rainshower
    82 -> Res.string.forecast_ui_violent_rain_showers to Res.drawable.ic_rainshower
    85 -> Res.string.forecast_ui_slight_snow_showers to Res.drawable.ic_snowy
    86 -> Res.string.forecast_ui_heavy_snow_showers to Res.drawable.ic_snowy
    95 -> Res.string.forecast_ui_moderate_thunderstorm to Res.drawable.ic_thunder
    96 -> Res.string.forecast_ui_thunderstorm_with_slight_hail to Res.drawable.ic_rainythunder
    99 -> Res.string.forecast_ui_thunderstorm_with_heavy_hail to Res.drawable.ic_rainythunder
    else -> Res.string.forecast_ui_clear_sky to Res.drawable.ic_sunny
}

internal data class WeatherDescription(
    val description: StringResource,
    val icon: DrawableResource,
)
