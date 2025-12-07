@file:OptIn(ExperimentalSerializationApi::class)

package com.multiplatform.weather.city.json

import com.multiplatform.weather.city.CountryCode
import com.multiplatform.weather.city.db.CityDao
import com.multiplatform.weather.city.loader.JsonDataSourceProvider
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verify.VerifyMode
import dev.mokkery.verifySuspend
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.okio.encodeToBufferedSink
import okio.Buffer
import okio.use
import kotlin.test.Test
import kotlin.test.assertEquals

internal class JsonCityDataLoaderImplTest {

    private val countryCode = CountryCode.getOrThrow("TR")

    private val jsonCity = JsonCity(
        id = 1,
        name = "Istanbul",
        asciiName = "Istanbul",
        countryName = "Turkey",
        countryCode = "TR",
        latitude = 41.0136,
        longitude = 28.955,
        adminName = "Istanbul",
    )

    private val json: Json = Json { ignoreUnknownKeys = true }

    private val sink by lazy {
        val output = Buffer()
        output.use {
            json.encodeToBufferedSink(listOf(jsonCity), it)
        }
        output
    }

    private val dao = mock<CityDao> {
        everySuspend { any("TR") } returns false
        everySuspend { saveOrUpdate(listOf(jsonCity.toData())) } returns Unit
    }

    private val jsonDataSourceProvider = mock<JsonDataSourceProvider> {
        everySuspend { invoke("tr") } returns sink
    }

    @Test
    fun `given data source than will populate database`() = runTest {
        val dataLoader = JsonCityDataLoaderImpl(
            dao = dao,
            sourceProvider = jsonDataSourceProvider,
            json = json,
        )

        val result = dataLoader(countryCode)

        assertEquals(Unit, result.getOrThrow())

        verifySuspend {
            dao.any("TR")
            dao.saveOrUpdate(listOf(jsonCity.toData()))
            jsonDataSourceProvider("tr")
        }
    }

    @Test
    fun `given data source than will skip populating database`() = runTest {
        val dao = mock<CityDao> {
            everySuspend { any("TR") } returns true
            everySuspend { saveOrUpdate(listOf(jsonCity.toData())) } returns Unit
        }

        val dataLoader = JsonCityDataLoaderImpl(
            dao = dao,
            sourceProvider = jsonDataSourceProvider,
            json = json,
        )

        val result = dataLoader(countryCode)

        assertEquals(Unit, result.getOrThrow())

        verifySuspend {
            dao.any("TR")
        }

        verifySuspend(mode = VerifyMode.not) {
            dao.saveOrUpdate(listOf(jsonCity.toData()))
            jsonDataSourceProvider("tr")
        }
    }
}
