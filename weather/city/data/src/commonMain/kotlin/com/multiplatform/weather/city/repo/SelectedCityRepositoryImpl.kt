package com.multiplatform.weather.city.repo

import com.multiplatform.td.core.datastore.DataStoreKey
import com.multiplatform.td.core.datastore.KeyedValueDataStore
import com.multiplatform.td.core.datastore.getSerializableFlow
import com.multiplatform.weather.city.City
import com.multiplatform.weather.city.json.JsonCity
import com.multiplatform.weather.city.json.toDomain
import com.multiplatform.weather.city.json.toJsonData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.reflect.typeOf

internal class SelectedCityRepositoryImpl(
    private val dataStore: KeyedValueDataStore,
) : SelectedCityRepository {

    private val dataStoreKey by lazy { DataStoreKey.defaultStoreKey("selected_cities") }

    override suspend fun save(cities: List<City>): Result<Unit> = runCatching {
        dataStore.setSerializable(
            dataStoreKey,
            typeOf<List<JsonCity>>(),
            cities.map { it.toJsonData() },
        )
            .getOrThrow()
    }

    override suspend fun cities(): Result<List<City>> = runCatching {
        val result = dataStore.getSerializable<List<JsonCity>>(dataStoreKey, typeOf<List<JsonCity>>())
        result.getOrThrow().map { it.toDomain() }
    }

    override fun asFlow(): Flow<Result<List<City>>> = dataStore.getSerializableFlow<List<JsonCity>>(dataStoreKey)
        .map { result ->
            result.fold(
                onSuccess = { Result.success(it.map { city -> city.toDomain() }) },
                onFailure = { Result.failure(it) },
            )
        }
}
