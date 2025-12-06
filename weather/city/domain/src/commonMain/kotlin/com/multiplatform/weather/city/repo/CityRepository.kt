package com.multiplatform.weather.city.repo

import com.multiplatform.weather.city.City
import com.multiplatform.weather.city.CountryCode

interface CityRepository {

    suspend fun saveOrUpdate(city: City): Result<Unit>
    suspend fun saveOrUpdate(cities: List<City>): Result<Unit>
    suspend fun cities(): Result<List<City>>
    suspend fun cities(countryCode: CountryCode): Result<List<City>>
    suspend fun any(): Result<Boolean>
    suspend fun any(countryCode: CountryCode): Result<Boolean>
    suspend fun delete(city: City): Result<Unit>
    suspend fun delete(cities: List<City>): Result<Unit>
}
