package com.multiplatform.weather.settings.inject

import com.multiplatform.td.core.injection.scopes.FeatureScope
import com.multiplatform.weather.city.inject.binder.SelectedCityRepositoryBinder
import com.multiplatform.weather.city.repo.SelectedCityRepository
import com.multiplatform.weather.settings.inject.binder.SettingsRepositoryBinder
import com.multiplatform.weather.settings.repo.SettingsRepository
import me.tatarka.inject.annotations.Provides

interface SettingsDataModule {

    val settingsRepository: SettingsRepository
    val selectedCityRepository: SelectedCityRepository

    @FeatureScope
    @Provides
    fun bindSettingsRepository(binder: SettingsRepositoryBinder): SettingsRepository = binder()

    @FeatureScope
    @Provides
    fun bindSelectedCityRepository(binder: SelectedCityRepositoryBinder): SelectedCityRepository = binder()
}
