@file:OptIn(ExperimentalTime::class)

package com.multiplatform.weather.forecast.today

import androidx.compose.runtime.Composable
import com.multiplatform.weather.city.City
import com.multiplatform.weather.core.measure.HumidityAmount
import com.multiplatform.weather.core.measure.PressureAmount
import com.multiplatform.weather.core.measure.TemperatureAmount
import com.multiplatform.weather.core.measure.UvIndexAmount
import com.multiplatform.weather.core.measure.WindAmount
import com.multiplatform.weather.forecast.Forecast
import com.multiplatform.weather.forecast.WeatherCode
import com.multiplatform.weather.settings.Settings
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import tdmultiplatform.weather.forecast.ui.generated.resources.Res
import tdmultiplatform.weather.forecast.ui.generated.resources.forecast_ui_next_day
import tdmultiplatform.weather.forecast.ui.generated.resources.forecast_ui_today
import tdmultiplatform.weather.forecast.ui.generated.resources.forecast_ui_tomorrow
import tdmultiplatform.weather.forecast.ui.generated.resources.forecast_ui_yesterday
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

internal data class ForecastState(
    val uiState: UiState = UiState.Loading,
    val forecast: Forecast = Forecast.Defaults,
    val settings: Settings = Settings.Defaults,
    val selectedCities: List<City> = emptyList(),
    val numberOfDays: Int = settings.days,
    val city: City = City.Defaults,
    val currentLocalDateTime: LocalDateTime = Clock.System.now().toLocalDateTime(
        timeZone = TimeZone.currentSystemDefault(),
    ),
) {

    private val date get() = currentLocalDateTime.date
    private val time get() = currentLocalDateTime.time
    private val hourlyWeather get() = requireNotNull(forecast.weather[date])
    private val currentWeather get() = hourlyWeather.first { it.time.hour == time.hour }
    private val dailyAverage get() = forecast.average.first { it.time == date }

    val selectedCityState get() = SelectedCityState(
        city = city,
        selectedCities = selectedCities,
    )

    val weatherDescriptionState get() = WeatherDescriptionState(
        temperature = currentWeather.temperature,
        weatherCode = currentWeather.code,
    )

    val weatherDetailDescriptionState get() =
        WeatherDetailDescriptionState(
            temperature = currentWeather.apparentTemperature,
            pressure = currentWeather.pressure,
            wind = currentWeather.wind,
            humidity = currentWeather.humidity,
            uvIndex = dailyAverage.uvIndex,
            sunset = dailyAverage.sunset.time,
            sunrise = dailyAverage.sunrise.time,
        )

    val weatherHourlyDescriptionState get() =
        WeatherHourlyDescriptionState(
            time = time,
            hourlyWeather = hourlyWeather.map {
                WeatherData(
                    time = it.time,
                    code = it.code,
                    temperature = it.temperature,
                )
            },
        )

    val weatherNextDayDescriptionStates get() = listOfNotNull(
        forecast.weather[date.plus(1, DateTimeUnit.DAY)]?.map {
            WeatherNextDayDescriptionState(
                date = date.plus(1, DateTimeUnit.DAY),
                temperature = it.temperature,
                weatherCode = it.code,
            )
        }?.first(),
        forecast.weather[date.plus(2, DateTimeUnit.DAY)]?.map {
            WeatherNextDayDescriptionState(
                date = date.plus(2, DateTimeUnit.DAY),
                temperature = it.temperature,
                weatherCode = it.code,
            )
        }?.first(),
    )
}

internal sealed interface UiState {

    data object Loading : UiState
    data object Success : UiState
    sealed interface Failure : UiState {
        data class Res(val stringResource: StringResource) : Failure
        data class Text(val message: String) : Failure
    }
}

internal data class WeatherDescriptionState(
    val temperature: TemperatureAmount,
    val weatherCode: WeatherCode,
)

internal data class WeatherNextDayDescriptionState(
    val temperature: TemperatureAmount,
    val weatherCode: WeatherCode,
    val date: LocalDate,
) {

    @Composable
    fun selectTitle(dateTime: LocalDateTime): String {
        val providedDate = dateTime.date
        val diff = date.minus(providedDate)
        return when (diff.days) {
            -1 -> stringResource(Res.string.forecast_ui_yesterday)
            0 -> stringResource(Res.string.forecast_ui_today)
            1 -> stringResource(Res.string.forecast_ui_tomorrow)
            else -> stringResource(Res.string.forecast_ui_next_day)
        }
    }
}

internal data class WeatherHourlyDescriptionState(
    val time: LocalTime,
    val hourlyWeather: List<WeatherData>,
) {
    val selectedPosition
        get() = hourlyWeather
            .indexOfFirst { time.hour == it.time.hour }
}

internal data class WeatherData(
    val time: LocalTime,
    val temperature: TemperatureAmount,
    val code: WeatherCode,
)

internal data class WeatherDetailDescriptionState(
    val temperature: TemperatureAmount,
    val pressure: PressureAmount,
    val wind: WindAmount,
    val humidity: HumidityAmount,
    val uvIndex: UvIndexAmount,
    val sunset: LocalTime,
    val sunrise: LocalTime,
)

internal data class SelectedCityState(
    val city: City,
    val selectedCities: List<City> = emptyList(),
)
