package com.multiplatform.todo.onboarding.usecase

import com.multiplatform.td.core.datastore.KeyedValueDataStoreException
import com.multiplatform.todo.onboarding.Onboarding
import com.multiplatform.todo.onboarding.repo.OnboardingRepository
import me.tatarka.inject.annotations.Inject

@Inject
class GetOnboardingUseCase(
    private val repository: OnboardingRepository,
) {

    suspend operator fun invoke(): Result<Onboarding> = runCatching {
        val result = repository.get()

        when (result.exceptionOrNull()) {
            is KeyedValueDataStoreException.NotFoundException -> Onboarding.Default
            else -> result.getOrThrow()
        }
    }
}
