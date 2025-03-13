package com.multiplatform.weather.city.repo

import com.multiplatform.weather.city.City
import kotlinx.coroutines.flow.Flow

interface SelectedCityRepository {

    suspend fun cities(): Result<List<City>>
    suspend fun save(cities: List<City>): Result<Unit>
    fun asFlow(): Flow<Result<List<City>>>
}