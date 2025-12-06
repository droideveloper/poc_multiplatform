package com.multiplatform.weather.city.repo

import com.multiplatform.weather.city.Country
import com.multiplatform.weather.city.loader.JsonCountryDataLoader
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFails

internal class CountryRepositoryImplTest {

    @Test
    fun `given success will return countries list`() = runTest {
        val dataLoader = mock<JsonCountryDataLoader> {
            everySuspend { invoke("countries") } returns Result.success(listOf(Country.Defaults))
        }

        val repo = CountryRepositoryImpl(dataLoader)

        val result = repo.countries()
        val actual = result.getOrThrow()

        assertContains(actual, Country.Defaults)

        verifySuspend {
            dataLoader("countries")
        }
    }

    @Test
    fun `given failure will return error`() = runTest {
        val error = Throwable("out of memory error")
        val dataLoader = mock<JsonCountryDataLoader> {
            everySuspend { invoke("countries") } returns Result.failure(error)
        }

        val repo = CountryRepositoryImpl(dataLoader)

        val result = repo.countries()
        val actual = assertFails { result.getOrThrow() }

        assertEquals(error, actual)

        verifySuspend {
            dataLoader("countries")
        }
    }
}
