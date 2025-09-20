package com.multiplatform.weather.forecast

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class AverageDto(
    @SerialName("time") val times: List<String>,
    @SerialName("sunset") val sunsets: List<String>,
    @SerialName("sunrise") val sunrises: List<String>,
    @SerialName("temperature_2m_max") val maxTemperatures: List<Double>,
    @SerialName("temperature_2m_min") val minTemperatures: List<Double>,
    @SerialName("apparent_temperature_max") val maxApparentTemperatures: List<Double>,
    @SerialName("apparent_temperature_min") val minApparentTemperatures: List<Double>,
    @SerialName("wind_speed_10m_max") val maxWindSpeeds: List<Double>,
    @SerialName("wind_speed_10m_min") val minWindSpeeds: List<Double>,
    @SerialName("uv_index_max") val uvIndexes: List<Double>,
)
