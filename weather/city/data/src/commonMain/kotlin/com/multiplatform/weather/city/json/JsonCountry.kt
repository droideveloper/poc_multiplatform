package com.multiplatform.weather.city.json

import com.multiplatform.weather.city.Country
import com.multiplatform.weather.city.CountryCode
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class JsonCountry(
    @SerialName("country") val countryName: String,
    @SerialName("iso2") val countryCode: String,
)

internal fun JsonCountry.toDomain(): Country =
    Country(
        name = countryName,
        code = CountryCode.getOrThrow(countryCode),
    )
