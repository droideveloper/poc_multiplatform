package com.multiplatform.weather.city.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.multiplatform.weather.city.City
import com.multiplatform.weather.city.Country
import com.multiplatform.weather.city.CountryCode
import com.multiplatform.weather.city.Location

@Entity(tableName = "cities")
internal data class CityDto(
    @PrimaryKey(autoGenerate = false) val id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "ascii_name") val asciiName: String,
    @ColumnInfo(name = "country_name", index = true) val countryName: String,
    @ColumnInfo(name = "country_code") val countryCode: String,
    val latitude: Double,
    val longitude: Double,
)

internal fun City.toData(): CityDto {
    val (countryName, countryCode) = country
    val (latitude, longitude) = location
    return CityDto(
        id = id,
        name = name,
        asciiName = displayName,
        countryName = countryName,
        countryCode = countryCode.value,
        latitude = latitude,
        longitude = longitude,
    )
}

internal fun CityDto.toDomain(): City = City(
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
