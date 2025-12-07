@file:OptIn(ExperimentalSerializationApi::class)

package com.multiplatform.weather.city.json

import com.multiplatform.weather.city.Country
import com.multiplatform.weather.city.loader.JsonDataSourceProvider
import dev.mokkery.answering.returns
import dev.mokkery.answering.throws
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.okio.encodeToBufferedSink
import okio.Buffer
import okio.use
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFails

internal class JsonCountryDataLoaderImplTest {

    private val jsonCountry = JsonCountry(
        countryName = "Turkey",
        countryCode = "TR",
    )

    private val json: Json = Json { ignoreUnknownKeys = true }

    private val sink by lazy {
        val output = Buffer()
        output.use {
            json.encodeToBufferedSink(listOf(jsonCountry), it)
        }
        output
    }

    private val jsonDataSourceProvider = mock<JsonDataSourceProvider> {
        everySuspend { invoke("countries") } returns sink
    }

    @Test
    fun `given success will load countries from json`() = runTest {
        val dataLoader = JsonCountryDataLoaderImpl(jsonDataSourceProvider, json)

        val result = dataLoader("countries")

        val actual = result.getOrThrow()

        assertContains(actual, Country.Defaults)

        verifySuspend { jsonDataSourceProvider("countries") }
    }

    @Test
    fun `given failure will return error`() = runTest {
        val error = Throwable("no such file error")
        val sourceProvider = mock<JsonDataSourceProvider> {
            everySuspend { invoke("countries") } throws error
        }
        val dataLoader = JsonCountryDataLoaderImpl(sourceProvider, json)

        val result = dataLoader("countries")

        val actual = assertFails { result.getOrThrow() }

        assertEquals(error, actual)

        verifySuspend { sourceProvider("countries") }
    }
}
