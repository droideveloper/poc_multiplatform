package com.multiplatform.weather.city

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

internal class CountryCodeTest {

    @Test
    fun `given empty code than will throw EmptyCountryCode error`() = runTest {
        val countryCode = CountryCode.get("")

        val actual = assertFails { countryCode.getOrThrow() }
        assertEquals(CountryCodeException.EmptyCountryCode, actual)
    }

    @Test
    fun `given invalid code than will throw InvalidCountryCode error`() = runTest {
        val countryCode = CountryCode.get("xxx")

        val actual = assertFails { countryCode.getOrThrow() }
        assertEquals(CountryCodeException.InvalidCountryCode, actual)
    }

    @Test
    fun `given valid code than will return CountryCode`() = runTest {
        val countryCode = CountryCode.getOrThrow("tr")

        assertEquals(countryCode.value, "tr")
    }
}
