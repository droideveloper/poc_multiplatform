package com.multiplatform.weather.onboarding.inject.binder

import com.multiplatform.td.core.datastore.KeyedValueDataStore
import com.multiplatform.td.core.injection.Binder
import com.multiplatform.weather.onboarding.repo.OnboardingRepository
import com.multiplatform.weather.onboarding.repo.OnboardingRepositoryImpl
import me.tatarka.inject.annotations.Inject

@Inject
class OnboardingRepositoryBinder(
    private val dataStore: KeyedValueDataStore,
) : Binder<OnboardingRepository> {

    override fun invoke(): OnboardingRepository =
        OnboardingRepositoryImpl(
            dataStore = dataStore,
        )
}
