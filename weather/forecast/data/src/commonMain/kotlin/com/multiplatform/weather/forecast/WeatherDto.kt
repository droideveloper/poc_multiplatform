package com.multiplatform.weather.forecast

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class WeatherDto(
    @SerialName("time") val times: List<String>,
    @SerialName("temperature_2m") val temperatures: List<Double>,
    @SerialName("apparent_temperature") val apparentTemperatures: List<Double>,
    @SerialName("relative_humidity_2m") val humidities: List<Int>,
    @SerialName("weather_code") val weatherCodes: List<Int>,
    @SerialName("wind_speed_10m") val windSpeeds: List<Double>,
    @SerialName("pressure_msl") val pressures: List<Double>,
)