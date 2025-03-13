package com.multiplatform.weather.forecast

import com.multiplatform.weather.core.measure.HumidityAmount
import com.multiplatform.weather.core.measure.PressureAmount
import com.multiplatform.weather.core.measure.TemperatureAmount
import com.multiplatform.weather.core.measure.WindAmount
import kotlinx.datetime.LocalTime

data class Weather(
    val time: LocalTime,
    val temperature: TemperatureAmount,
    val apparentTemperature: TemperatureAmount,
    val humidity: HumidityAmount,
    val wind: WindAmount,
    val pressure: PressureAmount,
    val code: WeatherCode,
)