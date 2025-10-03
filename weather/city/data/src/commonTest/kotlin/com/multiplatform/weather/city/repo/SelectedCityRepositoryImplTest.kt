package com.multiplatform.weather.city.repo

import com.multiplatform.td.core.datastore.DataStoreKey
import com.multiplatform.td.core.datastore.KeyedValueDataStore
import com.multiplatform.weather.city.City
import com.multiplatform.weather.city.Country
import com.multiplatform.weather.city.CountryCode
import com.multiplatform.weather.city.Location
import com.multiplatform.weather.city.json.JsonCity
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.reflect.typeOf
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFails

internal class SelectedCityRepositoryImplTest {

    private val jsonCity = JsonCity(
        id = 1,
        name = "Istanbul",
        asciiName = "Istanbul",
        countryName = "Turkey",
        countryCode = "TR",
        latitude = 41.0136,
        longitude = 28.955,
    )

    private val city = City(
        id = 1,
        name = "Istanbul",
        displayName = "Istanbul",
        location = Location(
            latitude = 41.0136,
            longitude = 28.955,
        ),
        country = Country(
            name = "Turkey",
            code = CountryCode.get("TR").getOrThrow(),
        ),
    )

    private val dataStoreKey by lazy { DataStoreKey.defaultStoreKey("selected_cities") }

    private var dataStore = mock<KeyedValueDataStore> {
        everySuspend {
            setSerializable(dataStoreKey, typeOf<List<JsonCity>>(), listOf(jsonCity))
        } returns Result.success(Unit)
        everySuspend {
            getSerializable<List<JsonCity>>(dataStoreKey, typeOf<List<JsonCity>>())
        } returns Result.success(listOf(jsonCity))
        every {
            getSerializableFlow<List<JsonCity>>(dataStoreKey, typeOf<List<JsonCity>>())
        } returns flowOf(Result.success(listOf(jsonCity)))
    }

    @Test
    fun `given save than will save cities locally`() = runTest {
        val repo = SelectedCityRepositoryImpl(dataStore)

        val result = repo.save(listOf(city))

        assertEquals(Unit, result.getOrThrow())

        verifySuspend {
            dataStore.setSerializable(dataStoreKey, typeOf<List<JsonCity>>(), listOf(jsonCity))
        }
    }

    @Test
    fun `given cities than will return local cities`() = runTest {
        val repo = SelectedCityRepositoryImpl(dataStore)

        val result = repo.cities()

        assertContains(result.getOrThrow(), city)

        verifySuspend {
            dataStore.getSerializable<List<JsonCity>>(dataStoreKey, typeOf<List<JsonCity>>())
        }
    }

    @Test
    fun `given cities flow than will return success cities flow`() = runTest {
        val repo = SelectedCityRepositoryImpl(dataStore)

        val cities = repo.asFlow()
        cities.collect { result ->
            assertContains(result.getOrThrow(), city)
        }

        verifySuspend {
            dataStore.getSerializableFlow<List<JsonCity>>(dataStoreKey, typeOf<List<JsonCity>>())
        }
    }

    @Test
    fun `given cities flow than will return failure cities flow`() = runTest {
        val error = Throwable()
        val dataStore = mock<KeyedValueDataStore> {
            every {
                getSerializableFlow<List<JsonCity>>(dataStoreKey, typeOf<List<JsonCity>>())
            } returns flowOf(Result.failure(error))
        }
        val repo = SelectedCityRepositoryImpl(dataStore)

        val cities = repo.asFlow()
        cities.collect { result ->
            val actual = assertFails { result.getOrThrow() }
            assertEquals(error, actual)
        }

        verifySuspend {
            dataStore.getSerializableFlow<List<JsonCity>>(dataStoreKey, typeOf<List<JsonCity>>())
        }
    }
}
