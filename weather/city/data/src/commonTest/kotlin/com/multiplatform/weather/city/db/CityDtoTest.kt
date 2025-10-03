package com.multiplatform.weather.city.db

import com.multiplatform.weather.city.City
import com.multiplatform.weather.city.Country
import com.multiplatform.weather.city.CountryCode
import com.multiplatform.weather.city.Location
import kotlin.test.Test
import kotlin.test.assertEquals

internal class CityDtoTest {

    private val cityDto = CityDto(
        id = 1,
        name = "Istanbul",
        asciiName = "Istanbul",
        countryName = "Turkey",
        countryCode = "TR",
        latitude = 41.0136,
        longitude = 28.955,
    )

    private val city = City(
        id = 1,
        name = "Istanbul",
        displayName = "Istanbul",
        location = Location(
            latitude = 41.0136,
            longitude = 28.955,
        ),
        country = Country(
            name = "Turkey",
            code = CountryCode.get("TR").getOrThrow(),
        ),
    )

    @Test
    fun `given dto toDomain than will return city`() {
        val actual = cityDto.toDomain()

        assertEquals(city.id, actual.id)
        assertEquals(city.name, actual.name)
        assertEquals(city.displayName, actual.displayName)
        assertEquals(city.location.latitude, actual.location.latitude)
        assertEquals(city.location.longitude, actual.location.longitude)
        assertEquals(city.country.name, actual.country.name)
        assertEquals(city.country.code, actual.country.code)
    }

    @Test
    fun `given city toData than will return dto`() {
        val actual = city.toData()

        assertEquals(cityDto.id, actual.id)
        assertEquals(cityDto.name, actual.name)
        assertEquals(cityDto.asciiName, actual.asciiName)
        assertEquals(cityDto.countryName, actual.countryName)
        assertEquals(cityDto.countryCode, actual.countryCode)
        assertEquals(cityDto.latitude, actual.latitude)
        assertEquals(cityDto.longitude, actual.longitude)
    }
}
