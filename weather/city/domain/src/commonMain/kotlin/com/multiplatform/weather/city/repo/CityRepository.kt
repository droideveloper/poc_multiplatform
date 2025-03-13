package com.multiplatform.weather.city.repo

import com.multiplatform.weather.city.City

interface CityRepository {

    suspend fun saveOrUpdate(city: City): Result<Unit>
    suspend fun saveOrUpdate(cities: List<City>): Result<Unit>
    suspend fun cities(): Result<List<City>>
    suspend fun any(): Result<Boolean>
    suspend fun delete(city: City): Result<Unit>
    suspend fun delete(cities: List<City>): Result<Unit>
}
