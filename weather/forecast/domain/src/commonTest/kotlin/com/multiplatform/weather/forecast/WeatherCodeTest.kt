package com.multiplatform.weather.forecast

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

internal class WeatherCodeTest {

    @Test
    fun `given valid weather code than will return WeatherCode`() {
        val result = WeatherCode.get(99)

        val actual = result.getOrThrow()
        assertEquals(99, actual.code)
    }

    @Test
    fun `given invalid weather code than will return error`() {
        val result = WeatherCode.get(120)

        val actual = assertFails { result.getOrThrow() }
        assertEquals(WeatherCodeException.Invalid, actual)
    }
}
