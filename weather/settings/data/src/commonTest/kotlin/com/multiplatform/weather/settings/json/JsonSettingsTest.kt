package com.multiplatform.weather.settings.json

import com.multiplatform.weather.core.measure.Humidity
import com.multiplatform.weather.core.measure.Pressure
import com.multiplatform.weather.core.measure.Speed
import com.multiplatform.weather.core.measure.Temperature
import com.multiplatform.weather.settings.Settings
import kotlin.test.Test
import kotlin.test.assertEquals

internal class JsonSettingsTest {

    private val temperatureUnits = mapOf(
        "°C" to Temperature.Celsius,
        "°F" to Temperature.Fahrenheit,
    )

    private val pressureUnits = mapOf(
        "hPa" to Pressure.MeanSeaLevel,
    )

    private val humidityUnits = mapOf(
        "%" to Humidity.Percentage,
    )

    private val windUnits = mapOf(
        "km/h" to Speed.KilometersPerHour,
        "m/s" to Speed.MetersPerSecond,
        "mph" to Speed.MilesPerHour,
        "knots" to Speed.Knots,
    )

    private val jsonSettings = JsonSettings(
        temperatureUnit = "°C",
        pressureUnit = "hPa",
        humidityUnit = "%",
        speedUnit = "km/h",
        days = 10,
    )

    private val settings = Settings(
        temperatureUnit = Temperature.Celsius,
        pressureUnit = Pressure.MeanSeaLevel,
        humidityUnit = Humidity.Percentage,
        speedUnit = Speed.KilometersPerHour,
        days = 10,
    )

    @Test
    fun `given valid temperature string will return Temperature`() = temperatureUnits.forEach { (input, expected) ->
        val actual = selectTemperatureUnit(input)

        assertEquals(expected, actual)
    }

    @Test
    fun `given valid pressure string will return Pressure`() = pressureUnits.forEach { (input, expected) ->
        val actual = selectPressureUnit(input)

        assertEquals(expected, actual)
    }

    @Test
    fun `given valid humidity string will return Humidity`() = humidityUnits.forEach { (input, expected) ->
        val actual = selectHumidityUnit(input)

        assertEquals(expected, actual)
    }

    @Test
    fun `given valid wind speed string will return Wind Speed`() = windUnits.forEach { (input, expected) ->
        val actual = selectSpeedUnit(input)

        assertEquals(expected, actual)
    }

    @Test
    fun `given json settings and toDomain invoked will return Settings`() {
        val actual = jsonSettings.toDomain()

        assertEquals(settings, actual)
    }

    @Test
    fun `given settings and toData invoked will return JsonSettings`() {
        val actual = settings.toData()

        assertEquals(jsonSettings, actual)
    }
}
