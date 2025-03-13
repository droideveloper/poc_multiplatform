package com.multiplatform.weather.city.json

import com.multiplatform.weather.city.City
import com.multiplatform.weather.city.Country
import com.multiplatform.weather.city.CountryCode
import com.multiplatform.weather.city.Location
import com.multiplatform.weather.city.db.CityDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class JsonCity(
    val id: Long,
    @SerialName("city") val name: String,
    @SerialName("city_ascii") val asciiName: String,
    @SerialName("lat") val latitude: Double,
    @SerialName("lng") val longitude: Double,
    @SerialName("country") val countryName: String,
    @SerialName("iso2") val countryCode: String,
)

internal fun JsonCity.toData() = CityDto(
    id = id,
    name = name,
    asciiName = asciiName,
    latitude = latitude,
    longitude = longitude,
    countryCode = countryCode,
    countryName = countryName,
)

internal fun City.toJsonData(): JsonCity {
    val (countryName, countryCode) = country
    val (latitude, longitude) = location
    return JsonCity(
        id = id,
        name = name,
        asciiName = displayName,
        countryName = countryName,
        countryCode = countryCode.value,
        latitude = latitude,
        longitude = longitude,
    )
}

internal fun JsonCity.toDomain() : City = City(
    id = id,
    name = name,
    displayName = asciiName,
    location = Location(
        latitude = latitude,
        longitude = longitude,
    ),
    country = Country(
        name = countryName,
        code = CountryCode.get(countryCode).getOrThrow(),
    ),
)
