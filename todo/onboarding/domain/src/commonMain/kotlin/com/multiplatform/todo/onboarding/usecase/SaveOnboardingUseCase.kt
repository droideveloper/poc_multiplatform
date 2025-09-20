package com.multiplatform.todo.onboarding.usecase

import com.multiplatform.todo.onboarding.Onboarding
import com.multiplatform.todo.onboarding.repo.OnboardingRepository
import me.tatarka.inject.annotations.Inject

@Inject
class SaveOnboardingUseCase(
    private val repository: OnboardingRepository,
) {

    suspend operator fun invoke(onboarding: Onboarding): Result<Unit> = runCatching {
        repository.save(onboarding)
            .getOrThrow()
    }
}
