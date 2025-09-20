package com.multiplatform.weather.onboarding.usecase

import com.multiplatform.weather.onboarding.repo.OnboardingRepository
import me.tatarka.inject.annotations.Inject

@Inject
class GetOnboardingUseCase(
    private val onboardingRepository: OnboardingRepository,
) {

    suspend operator fun invoke(): Result<Boolean> = runCatching {
        onboardingRepository.get()
            .getOrThrow()
    }
}
