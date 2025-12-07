package com.multiplatform.weather.city.json

import com.multiplatform.weather.city.Country
import kotlin.test.Test
import kotlin.test.assertEquals

internal class JsonCountryTest {

    private val jsonCountry = JsonCountry(
        countryName = "Turkey",
        countryCode = "TR",
    )

    @Test
    fun `given json country will map to country`() {
        val actual = jsonCountry.toDomain()

        assertEquals(Country.Defaults.name, actual.name)
        assertEquals(Country.Defaults.code, actual.code)
    }
}
