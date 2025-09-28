package com.multiplatform.weather.city.usecase

import com.multiplatform.weather.city.City
import com.multiplatform.weather.city.repo.SelectedCityRepository
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

internal class DeleteCityUseCaseTest {

    @Test
    fun `given success than will remove from local storage`() = runTest {
        val repository = mock<SelectedCityRepository> {
            everySuspend { cities() } returns Result.success(listOf(City.Defaults))
            everySuspend { save(emptyList()) } returns Result.success(Unit)
        }

        val useCase = DeleteCityUseCase(repository)
        val result = useCase(City.Defaults)

        assertEquals(Unit, result.getOrThrow())

        verifySuspend {
            repository.cities()
            repository.save(emptyList())
        }
    }

    @Test
    fun `given failure than will return error`() = runTest {
        val error = Throwable()
        val repository = mock<SelectedCityRepository> {
            everySuspend { cities() } returns Result.success(emptyList())
            everySuspend { save(emptyList()) } returns Result.failure(error)
        }

        val useCase = DeleteCityUseCase(repository)
        val result = useCase(City.Defaults)

        val actual = assertFails { result.getOrThrow() }
        assertEquals(error, actual)

        verifySuspend {
            repository.cities()
            repository.save(emptyList())
        }
    }
}
