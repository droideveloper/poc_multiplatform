package com.multiplatform.weather.city.json

import com.multiplatform.weather.city.City
import com.multiplatform.weather.city.Country
import com.multiplatform.weather.city.CountryCode
import com.multiplatform.weather.city.Location
import com.multiplatform.weather.city.db.CityDto
import com.multiplatform.weather.city.db.toDomain
import kotlin.test.Test
import kotlin.test.assertEquals

internal class JsonCityTest {

    private val cityDto = CityDto(
        id = 1,
        name = "Istanbul",
        asciiName = "Istanbul",
        countryName = "Turkey",
        countryCode = "TR",
        latitude = 41.0136,
        longitude = 28.955,
        adminName = "Istanbul",
    )

    private val jsonCity = JsonCity(
        id = 1,
        name = "Istanbul",
        asciiName = "Istanbul",
        countryName = "Turkey",
        countryCode = "TR",
        latitude = 41.0136,
        longitude = 28.955,
        adminName = "Istanbul",
    )

    private val city = City(
        id = 1,
        name = "Istanbul",
        displayName = "Istanbul",
        location = Location(
            latitude = 41.0136,
            longitude = 28.955,
        ),
        adminName = "Istanbul",
        country = Country(
            name = "Turkey",
            code = CountryCode.get("TR").getOrThrow(),
        ),
    )

    @Test
    fun `given json city than toData will return CityDto`() {
        val actual = jsonCity.toData()

        assertEquals(cityDto.id, actual.id)
        assertEquals(cityDto.name, actual.name)
        assertEquals(cityDto.asciiName, actual.asciiName)
        assertEquals(cityDto.adminName, actual.adminName)
        assertEquals(cityDto.countryName, actual.countryName)
        assertEquals(cityDto.countryCode, actual.countryCode)
        assertEquals(cityDto.latitude, actual.latitude)
        assertEquals(cityDto.longitude, actual.longitude)
    }

    @Test
    fun `given city toJsonData than will return jsonCity`() {
        val actual = city.toJsonData()

        assertEquals(jsonCity.id, actual.id)
        assertEquals(jsonCity.name, actual.name)
        assertEquals(jsonCity.asciiName, actual.asciiName)
        assertEquals(jsonCity.adminName, actual.adminName)
        assertEquals(jsonCity.countryName, actual.countryName)
        assertEquals(jsonCity.countryCode, actual.countryCode)
        assertEquals(jsonCity.latitude, actual.latitude)
        assertEquals(jsonCity.longitude, actual.longitude)
    }

    @Test
    fun `given jsonCity toDomain than will return city`() {
        val actual = jsonCity.toDomain()

        assertEquals(city.id, actual.id)
        assertEquals(city.name, actual.name)
        assertEquals(city.displayName, actual.displayName)
        assertEquals(city.adminName, actual.adminName)
        assertEquals(city.location.latitude, actual.location.latitude)
        assertEquals(city.location.longitude, actual.location.longitude)
        assertEquals(city.country.name, actual.country.name)
        assertEquals(city.country.code, actual.country.code)
    }
}
