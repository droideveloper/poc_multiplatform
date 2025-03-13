package com.multiplatform.weather.onboarding.inject

import com.multiplatform.td.core.injection.scopes.FeatureScope
import com.multiplatform.weather.city.inject.binder.SelectedCityRepositoryBinder
import com.multiplatform.weather.city.repo.SelectedCityRepository
import com.multiplatform.weather.onboarding.inject.binder.OnboardingRepositoryBinder
import com.multiplatform.weather.onboarding.repo.OnboardingRepository
import com.multiplatform.weather.settings.inject.binder.SettingsRepositoryBinder
import com.multiplatform.weather.settings.repo.SettingsRepository
import me.tatarka.inject.annotations.Provides

interface OnboardingDataModule {
    val onboardingRepository: OnboardingRepository
    val selectedCityRepository: SelectedCityRepository
    val settingsRepository: SettingsRepository

    @FeatureScope
    @Provides
    fun bindOnboardingRepository(binder: OnboardingRepositoryBinder): OnboardingRepository = binder()

    @FeatureScope
    @Provides
    fun bindSelectedCityRepository(binder: SelectedCityRepositoryBinder): SelectedCityRepository = binder()

    @FeatureScope
    @Provides
    fun bindSettingsRepository(binder: SettingsRepositoryBinder): SettingsRepository = binder()
}
