@file:OptIn(ExperimentalTime::class)

package com.multiplatform.weather.forecast.cache

import com.multiplatform.td.core.datastore.DataStoreKey
import com.multiplatform.td.core.datastore.KeyedValueDataStore
import com.multiplatform.td.core.datastore.KeyedValueDataStoreException
import com.multiplatform.td.core.repository.CacheException
import com.multiplatform.weather.forecast.LocalDateTimeProvider
import com.multiplatform.weather.forecast.service.forecastDto
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verify
import dev.mokkery.verifySuspend
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlin.reflect.typeOf
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.ExperimentalTime

internal class ForecastDataStoreCacheTest {

    private val key = "city_forecast"
    private val dataStoreKey = DataStoreKey.defaultStoreKey(key)

    private val dateTime = LocalDateTime(
        date = LocalDate(2025, 7, 15),
        time = LocalTime(12, 0),
    )

    private val localDateTimeProvider = mock<LocalDateTimeProvider> {
        every { invoke() } returns dateTime
    }

    @Test
    fun `given expired cache than will return error`() = runTest {
        val dataStore = mock<KeyedValueDataStore> {
            everySuspend {
                getSerializable<InstantValue>(
                    key = dataStoreKey,
                    valueType = typeOf<InstantValue>(),
                )
            } returns Result.success(
                value = InstantValue(
                    time = dateTime.toInstant(TimeZone.UTC).minus(2.days),
                    value = forecastDto,
                ),
            )
        }

        val cache = ForecastDataStoreCache(dataStore, localDateTimeProvider, key)

        val result = cache.get(36.hours)
        val actual = assertFails { result.getOrThrow() }

        assertEquals(CacheException.Expired, actual)

        verifySuspend {
            dataStore.getSerializable<InstantValue>(
                key = dataStoreKey,
                valueType = typeOf<InstantValue>(),
            )
        }
        verify { localDateTimeProvider() }
    }

    @Test
    fun `given no cache than will return error`() = runTest {
        val dataStore = mock<KeyedValueDataStore> {
            everySuspend {
                getSerializable<InstantValue>(
                    key = dataStoreKey,
                    valueType = typeOf<InstantValue>(),
                )
            } returns Result.failure(
                exception = KeyedValueDataStoreException.NotFoundException(key),
            )
        }

        val cache = ForecastDataStoreCache(dataStore, localDateTimeProvider, key)

        val result = cache.get(36.hours)
        val actual = assertFails { result.getOrThrow() }

        assertEquals(CacheException.Empty, actual)

        verifySuspend {
            dataStore.getSerializable<InstantValue>(
                key = dataStoreKey,
                valueType = typeOf<InstantValue>(),
            )
        }
    }

    @Test
    fun `given cache than will return cached response`() = runTest {
        val dataStore = mock<KeyedValueDataStore> {
            everySuspend {
                getSerializable<InstantValue>(
                    key = dataStoreKey,
                    valueType = typeOf<InstantValue>(),
                )
            } returns Result.success(
                value = InstantValue(
                    time = dateTime.toInstant(TimeZone.UTC).minus(1.days),
                    value = forecastDto,
                ),
            )
        }

        val cache = ForecastDataStoreCache(dataStore, localDateTimeProvider, key)

        val result = cache.get(36.hours)
        assertEquals(forecastDto, result.getOrThrow())

        verifySuspend {
            dataStore.getSerializable<InstantValue>(
                key = dataStoreKey,
                valueType = typeOf<InstantValue>(),
            )
        }
        verify { localDateTimeProvider() }
    }

    @Test
    fun `given clear than will remove cache`() = runTest {
        val dataStore = mock<KeyedValueDataStore> {
            everySuspend {
                removeValue(dataStoreKey, null)
            } returns Result.success(Unit)
        }

        val cache = ForecastDataStoreCache(dataStore, localDateTimeProvider, key)

        cache.clear()

        verifySuspend {
            dataStore.removeValue(dataStoreKey, null)
        }
    }

    @Test
    fun `given put than will store cache`() = runTest {
        val dataStore = mock<KeyedValueDataStore> {
            everySuspend {
                setSerializable(
                    key = dataStoreKey,
                    valueType = typeOf<InstantValue>(),
                    value = InstantValue(
                        time = dateTime.toInstant(TimeZone.UTC),
                        value = forecastDto,
                    ),
                )
            } returns Result.success(Unit)
        }

        val cache = ForecastDataStoreCache(dataStore, localDateTimeProvider, key)

        cache.put(forecastDto)

        verifySuspend {
            dataStore.setSerializable(
                key = dataStoreKey,
                valueType = typeOf<InstantValue>(),
                value = InstantValue(
                    time = dateTime.toInstant(TimeZone.UTC),
                    value = forecastDto,
                ),
            )
        }
    }
}
