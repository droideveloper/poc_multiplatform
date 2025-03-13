package com.multiplatform.weather.city.repo

import com.multiplatform.weather.city.City
import com.multiplatform.weather.city.db.CityDao
import com.multiplatform.weather.city.db.toData
import com.multiplatform.weather.city.db.toDomain

internal class CityRepositoryImpl(
    private val dao: CityDao,
) : CityRepository {

    override suspend fun saveOrUpdate(city: City): Result<Unit> = runCatching {
        dao.saveOrUpdate(city.toData())
    }

    override suspend fun saveOrUpdate(cities: List<City>): Result<Unit> = runCatching {
        dao.saveOrUpdate(dto = cities.map { it.toData() })
    }

    override suspend fun cities(): Result<List<City>> = runCatching {
        val cities = dao.cities()
        cities.map { it.toDomain() }
    }

    override suspend fun any(): Result<Boolean> = runCatching {
        dao.any()
    }

    override suspend fun delete(city: City): Result<Unit> = runCatching {
        dao.delete(city.toData())
    }

    override suspend fun delete(cities: List<City>): Result<Unit> = runCatching {
        dao.delete(cities.map { it.toData() })
    }
}
