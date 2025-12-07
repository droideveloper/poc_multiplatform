package com.multiplatform.weather.city

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class CityTest {

    @Test
    fun `given valid city than will generate valid key`() = runTest {
        val location = Location.Defaults
        val country = Country(
            name = "Turkey",
            code = CountryCode.getOrThrow("TR"),
        )
        val city = City(
            id = 1L,
            name = "Adalar",
            displayName = "Istanbul",
            country = country,
            location = location,
            adminName = "Istanbul",
        )

        assertEquals("adalar_istanbul_turkey", city.createKey())
        assertEquals("Adalar, Istanbul", city.cityName)

        val newCity = city.copy(name = "Istanbul")
        assertEquals("Istanbul", newCity.cityName)
        assertEquals("istanbul_turkey", newCity.createKey())
    }
}
