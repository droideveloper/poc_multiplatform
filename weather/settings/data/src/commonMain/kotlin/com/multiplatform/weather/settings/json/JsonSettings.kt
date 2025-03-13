package com.multiplatform.weather.settings.json

import com.multiplatform.weather.core.measure.Humidity
import com.multiplatform.weather.core.measure.Pressure
import com.multiplatform.weather.core.measure.Speed
import com.multiplatform.weather.core.measure.Temperature
import com.multiplatform.weather.settings.Settings
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class JsonSettings(
    @SerialName("temperature_unit") val temperatureUnit: String,
    @SerialName("pressure_unit") val pressureUnit: String,
    @SerialName("humidity_unit") val humidityUnit: String,
    @SerialName("speed_unit") val speedUnit: String,
    @SerialName("days") val days: Int,
)

internal fun JsonSettings.toDomain() = Settings(
    temperatureUnit = selectTemperatureUnit(temperatureUnit),
    pressureUnit = selectPressureUnit(pressureUnit),
    humidityUnit = selectHumidityUnit(humidityUnit),
    speedUnit = selectSpeedUnit(speedUnit),
    days = days,
)

internal fun Settings.toData() = JsonSettings(
    temperatureUnit = temperatureUnit.toString(),
    pressureUnit = pressureUnit.toString(),
    humidityUnit = humidityUnit.toString(),
    speedUnit = speedUnit.toString(),
    days = days,
)

internal fun selectTemperatureUnit(unit: String) = when (unit) {
    "°C" -> Temperature.Celsius
    "°F" -> Temperature.Fahrenheit
    else -> throw IllegalArgumentException("$unit is not valid temperature unit")
}

internal fun selectPressureUnit(unit: String) = when (unit) {
    "hPa" -> Pressure.MeanSeaLevel
    else -> throw IllegalArgumentException("$unit is not valid pressure unit")
}

internal fun selectHumidityUnit(unit: String) = when (unit) {
    "%" -> Humidity.Percentage
    else -> throw IllegalArgumentException("$unit is not valid humidity unit")
}

internal fun selectSpeedUnit(unit: String) = when (unit) {
    "km/h" -> Speed.KilometersPerHour
    "m/s" -> Speed.MetersPerSecond
    "mph" -> Speed.MilesPerHour
    "knots" -> Speed.Knots
    else -> throw IllegalArgumentException("$unit is not valid speed unit")
}