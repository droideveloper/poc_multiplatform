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
            name = "istanbul",
            displayName = "Istanbul",
            country = country,
            location = location,
        )

        assertEquals("istanbul_turkey", city.createKey())
    }
}
