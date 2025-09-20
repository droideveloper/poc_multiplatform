@file:OptIn(ExperimentalTime::class)

package com.multiplatform.weather.forecast

import com.multiplatform.weather.core.measure.HumidityAmount
import com.multiplatform.weather.core.measure.PressureAmount
import com.multiplatform.weather.core.measure.TemperatureAmount
import com.multiplatform.weather.core.measure.UvIndexAmount
import com.multiplatform.weather.core.measure.WindAmount
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.ExperimentalTime

@Serializable
internal data class ForecastDto(
    @SerialName("hourly") val weather: WeatherDto,
    @SerialName("daily") val average: AverageDto,
)

internal fun ForecastDto.toDomain(): Forecast =
    Forecast(
        weather = weather.toDomain(),
        average = average.toDomain(),

    )

internal fun WeatherDto.toDomain(): Map<LocalDate, List<Weather>> =
    times.mapIndexed { index, time ->
        val dateTime = LocalDateTime.parse(time, LocalDateTime.Formats.ISO)

        val temperature = temperatures[index]
        val apparentTemperature = apparentTemperatures[index]
        val weatherCode = weatherCodes[index]
        val pressure = pressures[index]
        val humidity = humidities[index]
        val windSpeed = windSpeeds[index]

        WeatherObject(
            localDate = dateTime.date,
            data = Weather(
                time = dateTime.time,
                temperature = TemperatureAmount.celsius(temperature),
                apparentTemperature = TemperatureAmount.celsius(apparentTemperature),
                code = WeatherCode.getOrThrow(weatherCode),
                pressure = PressureAmount.of(pressure),
                wind = WindAmount.kmh(windSpeed),
                humidity = HumidityAmount.of(humidity),
            ),
        )
    }
        .groupBy {
            it.localDate
        }
        .mapValues { it.value.map { o -> o.data } }

internal data class WeatherObject(
    val localDate: LocalDate,
    val data: Weather,
)

internal fun AverageDto.toDomain(): List<Average> =
    this.times.mapIndexed { index, time ->
        val date = LocalDate.parse(time, LocalDate.Formats.ISO)

        val sunrise = LocalDateTime.parse(sunrises[index], LocalDateTime.Formats.ISO)
        val sunset = LocalDateTime.parse(sunsets[index], LocalDateTime.Formats.ISO)

        val timeZone = TimeZone.of("GMT+0000")
        val sunriseInstant = sunrise.toInstant(timeZone)
        val sunsetInstant = sunset.toInstant(timeZone)

        val minTemperature = minTemperatures[index]
        val maxTemperature = maxTemperatures[index]
        val minApparentTemperature = minApparentTemperatures[index]
        val maxApparentTemperature = maxApparentTemperatures[index]
        val maxWindSpeed = maxWindSpeeds[index]
        val minWindSpeed = minWindSpeeds[index]
        val uvIndex = uvIndexes[index]

        Average(
            time = date,
            sunset = sunsetInstant.toLocalDateTime(TimeZone.currentSystemDefault()),
            sunrise = sunriseInstant.toLocalDateTime(TimeZone.currentSystemDefault()),
            minTemperature = TemperatureAmount.celsius(minTemperature),
            maxTemperature = TemperatureAmount.celsius(maxTemperature),
            minApparentTemperature = TemperatureAmount.celsius(minApparentTemperature),
            maxApparentTemperature = TemperatureAmount.celsius(maxApparentTemperature),
            minWindSpeed = WindAmount.kmh(minWindSpeed),
            maxWindSpeed = WindAmount.kmh(maxWindSpeed),
            uvIndex = UvIndexAmount.of(uvIndex),
        )
    }
