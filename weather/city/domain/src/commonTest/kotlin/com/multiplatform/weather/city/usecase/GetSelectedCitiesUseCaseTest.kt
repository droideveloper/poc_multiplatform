package com.multiplatform.weather.city.usecase

import com.multiplatform.weather.city.City
import com.multiplatform.weather.city.repo.SelectedCityRepository
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFails

internal class GetSelectedCitiesUseCaseTest {

    @Test
    fun `given success than will return selected cities`() = runTest {
        val repository = mock<SelectedCityRepository> {
            everySuspend { cities() } returns Result.success(listOf(City.Defaults))
        }

        val useCase = GetSelectedCitiesUseCase(repository)
        val result = useCase()

        assertContains(result.getOrThrow(), City.Defaults)

        verifySuspend { repository.cities() }
    }

    @Test
    fun `given failure than will return error`() = runTest {
        val error = Throwable()
        val repository = mock<SelectedCityRepository> {
            everySuspend { cities() } returns Result.failure(error)
        }

        val useCase = GetSelectedCitiesUseCase(repository)
        val result = useCase()

        val actual = assertFails { result.getOrThrow() }
        assertEquals(error, actual)

        verifySuspend { repository.cities() }
    }

    @Test
    fun `given flow success than will return selected cities`() = runTest {
        val repository = mock<SelectedCityRepository> {
            every { asFlow() } returns flowOf(Result.success(listOf(City.Defaults)))
        }

        val useCase = GetSelectedCitiesUseCase(repository)
        val flow = useCase.asFlow()

        flow.collect { result ->
            assertContains(result.getOrThrow(), City.Defaults)
        }
    }

    @Test
    fun `given flow failure than will return error`() = runTest {
        val error = Throwable()
        val repository = mock<SelectedCityRepository> {
            every { asFlow() } returns flowOf(Result.failure(error))
        }

        val useCase = GetSelectedCitiesUseCase(repository)
        val flow = useCase.asFlow()

        flow.collect { result ->
            val actual = assertFails { result.getOrThrow() }
            assertEquals(error, actual)
        }
    }
}
