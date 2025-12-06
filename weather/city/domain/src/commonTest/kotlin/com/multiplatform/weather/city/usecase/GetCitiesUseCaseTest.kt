package com.multiplatform.weather.city.usecase

import com.multiplatform.weather.city.City
import com.multiplatform.weather.city.CountryCode
import com.multiplatform.weather.city.repo.CityRepository
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFails

internal class GetCitiesUseCaseTest {

    private val countryCode = CountryCode.getOrThrow("TR")

    @Test
    fun `given success than will return result of cities`() = runTest {
        val repository = mock<CityRepository> {
            everySuspend { cities(countryCode) } returns Result.success(listOf(City.Defaults))
        }

        val useCase = GetCitiesUseCase(repository)
        val result = useCase(countryCode)

        assertContains(result.getOrThrow(), City.Defaults)

        verifySuspend { repository.cities(countryCode) }
    }

    @Test
    fun `given error than will return result of error`() = runTest {
        val error = Throwable()
        val repository = mock<CityRepository> {
            everySuspend { cities(countryCode) } returns Result.failure(error)
        }

        val useCase = GetCitiesUseCase(repository)
        val result = useCase(countryCode)

        val actual = assertFails { result.getOrThrow() }
        assertEquals(error, actual)

        verifySuspend { repository.cities(countryCode) }
    }
}
