package com.multiplatform.weather.settings

import com.multiplatform.weather.core.measure.Humidity
import com.multiplatform.weather.core.measure.Pressure
import com.multiplatform.weather.core.measure.Speed
import com.multiplatform.weather.core.measure.Temperature

data class Settings(
    val temperatureUnit: Temperature,
    val pressureUnit: Pressure,
    val humidityUnit: Humidity,
    val speedUnit: Speed,
    val days: Int = 10,
) {

    companion object {

        val Defaults = Settings(
            temperatureUnit = Temperature.Celsius,
            pressureUnit = Pressure.MeanSeaLevel,
            humidityUnit = Humidity.Percentage,
            speedUnit = Speed.KilometersPerHour,
        )
    }
}
