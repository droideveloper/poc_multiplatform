package com.multiplatform.weather.onboarding.usecase

import com.multiplatform.weather.onboarding.repo.OnboardingRepository
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertTrue

internal class GetOnboardingUseCaseTest {

    @Test
    fun `given read onboarding status than will return result`() = runTest {
        val repository = mock<OnboardingRepository> {
            everySuspend { get() } returns Result.success(true)
        }

        val useCase = GetOnboardingUseCase(repository)

        val result = useCase()

        assertTrue(result.getOrThrow())

        verifySuspend {
            repository.get()
        }
    }

    @Test
    fun `given read onboarding status than will return failure`() = runTest {
        val error = Exception()
        val repository = mock<OnboardingRepository> {
            everySuspend { get() } returns Result.failure(error)
        }

        val useCase = GetOnboardingUseCase(repository)

        val result = useCase()

        val actual = assertFails { result.getOrThrow() }
        assertEquals(error, actual)

        verifySuspend {
            repository.get()
        }
    }
}
