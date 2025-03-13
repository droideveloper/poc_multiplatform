package com.multiplatform.weather.onboarding.usecase

import com.multiplatform.weather.onboarding.repo.OnboardingRepository
import me.tatarka.inject.annotations.Inject

@Inject
class SaveOnboardingUseCase(
    private val onboardingRepository: OnboardingRepository,
) {

    suspend operator fun invoke(isCompleted: Boolean): Result<Unit> = runCatching {
        onboardingRepository.save(isCompleted)
            .getOrThrow()
    }
}