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

internal class SaveOnboardingUseCaseTest {

    @Test
    fun `given write onboarding status than will return success`() = runTest {
        val repository = mock<OnboardingRepository> {
            everySuspend { save(isCompleted = true) } returns Result.success(Unit)
        }

        val useCase = SaveOnboardingUseCase(repository)

        val result = useCase(isCompleted = true)

        assertEquals(Unit, result.getOrThrow())

        verifySuspend {
            repository.save(isCompleted = true)
        }
    }

    @Test
    fun `given write onboarding status than will return failure`() = runTest {
        val error = Exception()
        val repository = mock<OnboardingRepository> {
            everySuspend { save(isCompleted = true) } returns Result.failure(error)
        }

        val useCase = SaveOnboardingUseCase(repository)

        val result = useCase(isCompleted = true)

        val actual = assertFails { result.getOrThrow() }
        assertEquals(error, actual)

        verifySuspend {
            repository.save(isCompleted = true)
        }
    }
}
