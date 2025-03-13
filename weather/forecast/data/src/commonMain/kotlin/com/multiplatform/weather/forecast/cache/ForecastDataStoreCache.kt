@file:OptIn(ExperimentalTime::class)

package com.multiplatform.weather.forecast.cache

import com.multiplatform.td.core.datastore.DataStoreKey
import com.multiplatform.td.core.datastore.KeyedValueDataStore
import com.multiplatform.td.core.datastore.getSerializable
import com.multiplatform.td.core.datastore.setSerializable
import com.multiplatform.td.core.repository.Cache
import com.multiplatform.td.core.repository.CacheException
import com.multiplatform.weather.forecast.ForecastDto
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

internal class ForecastDataStoreCache(
    private val dataStore: KeyedValueDataStore,
    key: String,
) : Cache<ForecastDto> {

    private val dataStoreKey by lazy { DataStoreKey.defaultStoreKey(key) }

    override suspend fun get(maxAge: Duration): Result<ForecastDto> {
        val result: Result<InstantValue> = dataStore.getSerializable(dataStoreKey)
        val cache = result.getOrNull()
        return cache
            ?.let { (instant, value) ->
                value.takeIf { now().minus(instant) <= maxAge }
            }
            ?.let { Result.success(it) }
            ?: when {
                cache == null -> Result.failure(CacheException.Empty)
                else -> Result.failure(CacheException.Expired)
            }
    }

    override suspend fun clear() {
        dataStore.removeValue(dataStoreKey, null)
            .getOrThrow()
    }

    override suspend fun put(value: ForecastDto) {
        dataStore.setSerializable(dataStoreKey, InstantValue.now(value))
            .getOrThrow()
    }

    private fun now() = Clock.System.now()
}