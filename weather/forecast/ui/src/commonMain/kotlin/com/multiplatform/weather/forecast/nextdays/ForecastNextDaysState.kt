@file:OptIn(ExperimentalTime::class)

package com.multiplatform.weather.forecast.nextdays

import com.multiplatform.weather.city.City
import com.multiplatform.weather.forecast.Forecast
import com.multiplatform.weather.forecast.today.UiState
import com.multiplatform.weather.forecast.today.WeatherData
import com.multiplatform.weather.forecast.today.WeatherDescriptionState
import com.multiplatform.weather.forecast.today.WeatherHourlyDescriptionState
import com.multiplatform.weather.forecast.today.WeatherNextDayDescriptionState
import com.multiplatform.weather.settings.Settings
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

internal data class ForecastNextDaysState(
    val uiState: UiState = UiState.Loading,
    val forecast: Forecast = Forecast.Defaults,
    val settings: Settings = Settings.Defaults,
    val city: City = City.Defaults,
    val currentLocalDateTime: LocalDateTime = Clock.System.now().toLocalDateTime(
        timeZone = TimeZone.currentSystemDefault(),
    ),
) {

    private val date get() = currentLocalDateTime.date
    private val time get() = currentLocalDateTime.time
    private val hourlyWeather get() = requireNotNull(forecast.weather[date])
    private val currentWeather get() = hourlyWeather.first { it.time.hour == time.hour }

    val weatherDescriptionState get() = WeatherDescriptionState(
        temperature = currentWeather.temperature,
        weatherCode = currentWeather.code,
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

    val weatherNextDayDescriptionStates get() =
        (1..settings.days).mapNotNull { index ->
            forecast.weather[date.plus(index, DateTimeUnit.DAY)]?.map {
                WeatherNextDayDescriptionState(
                    date = date.plus(index, DateTimeUnit.DAY),
                    temperature = it.temperature,
                    weatherCode = it.code,
                )
            }?.first()
        }
}
