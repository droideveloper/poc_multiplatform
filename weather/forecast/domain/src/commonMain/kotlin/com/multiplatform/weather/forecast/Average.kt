package com.multiplatform.weather.forecast

import com.multiplatform.weather.core.measure.TemperatureAmount
import com.multiplatform.weather.core.measure.UvIndexAmount
import com.multiplatform.weather.core.measure.WindAmount
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

data class Average(
    val time: LocalDate,
    val sunset: LocalDateTime,
    val sunrise: LocalDateTime,
    val minTemperature: TemperatureAmount,
    val maxTemperature: TemperatureAmount,
    val minApparentTemperature: TemperatureAmount,
    val maxApparentTemperature: TemperatureAmount,
    val minWindSpeed: WindAmount,
    val maxWindSpeed: WindAmount,
    val uvIndex: UvIndexAmount,
)
