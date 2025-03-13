package com.multiplatform.weather.city.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
internal interface CityDao {

    @Upsert
    suspend fun saveOrUpdate(dto: CityDto)

    @Upsert
    suspend fun saveOrUpdate(dto: List<CityDto>)

    @Query("SELECT * FROM cities")
    suspend fun cities(): List<CityDto>

    @Query("SELECT COUNT(*) FROM cities")
    suspend fun any(): Boolean

    @Delete
    suspend fun delete(dto: CityDto)

    @Delete
    suspend fun delete(dto: List<CityDto>)
}

internal object MockDao {

    fun create(
        initialData: List<CityDto> = cities,
    ): CityDao =
        object : CityDao {
            private val list = mutableListOf<CityDto>()

            init {
                list.addAll(initialData)
            }

            override suspend fun saveOrUpdate(dto: CityDto) {
                val index = list.indexOfFirst { it.id == dto.id }
                when {
                    index == -1 -> list.add(dto)
                    else -> list[index] = dto
                }
            }

            override suspend fun saveOrUpdate(dto: List<CityDto>) {
                dto.forEach { saveOrUpdate(it) }
            }

            override suspend fun cities(): List<CityDto> = list

            override suspend fun any(): Boolean = list.isNotEmpty()

            override suspend fun delete(dto: CityDto) {
                val index = list.indexOfFirst { it.id == dto.id }
                when {
                    index == -1 -> Unit
                    else -> list.remove(dto)
                }
            }

            override suspend fun delete(dto: List<CityDto>) {
                dto.forEach { delete(it) }
            }
        }

    private val cities = listOf(
        CityDto(
            id = 1,
            name = "Istanbul",
            asciiName = "Istanbul",
            countryName = "Turkey",
            countryCode = "TR",
            latitude = 41.0136,
            longitude = 28.955,
        ),
        CityDto(
            id = 2,
            name = "Ankara",
            asciiName = "Ankara",
            countryName = "Turkey",
            countryCode = "TR",
            latitude = 39.93,
            longitude = 32.85,
        ),
        CityDto(
            id = 3,
            name = "Izmir",
            asciiName = "Izmir",
            countryName = "Turkey",
            countryCode = "TR",
            latitude = 38.42,
            longitude = 27.14,
        ),
    )
}
