package com.multiplatform.weather.city.usecase

import com.multiplatform.weather.city.Country
import com.multiplatform.weather.city.repo.CountryRepository
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFails

internal class GetCountriesUseCaseTest {

    @Test
    fun `given success than will return result of countries`() = runTest {
        val repository = mock<CountryRepository> {
            everySuspend { countries() } returns Result.success(listOf(Country.Defaults))
        }

        val useCase = GetCountriesUseCase(repository)
        val result = useCase()

        assertContains(result.getOrThrow(), Country.Defaults)

        verifySuspend {
            repository.countries()
        }
    }

    @Test
    fun `given error than will return result of error`() = runTest {
        val error = Throwable("serialization error")
        val repository = mock<CountryRepository> {
            everySuspend { countries() } returns Result.failure(error)
        }

        val useCase = GetCountriesUseCase(repository)
        val result = useCase()

        val actual = assertFails { result.getOrThrow() }
        assertEquals(error, actual)

        verifySuspend {
            repository.countries()
        }
    }
}
