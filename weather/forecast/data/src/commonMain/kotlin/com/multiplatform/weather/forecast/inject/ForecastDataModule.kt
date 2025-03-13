package com.multiplatform.weather.forecast.inject

import com.multiplatform.td.core.datastore.KeyedValueDataStore
import com.multiplatform.td.core.injection.scopes.FeatureScope
import com.multiplatform.weather.city.inject.binder.SelectedCityRepositoryBinder
import com.multiplatform.weather.city.repo.SelectedCityRepository
import com.multiplatform.weather.forecast.inject.binder.ForecastRepositoryBinder
import com.multiplatform.weather.forecast.repo.ForecastRepository
import com.multiplatform.weather.settings.inject.binder.SettingsRepositoryBinder
import com.multiplatform.weather.settings.repo.SettingsRepository
import me.tatarka.inject.annotations.Provides

interface ForecastDataModule {

    val forecastRepository: ForecastRepository
    val settingsRepository: SettingsRepository
    val selectedCityRepository: SelectedCityRepository
    val dataStore: KeyedValueDataStore

    @FeatureScope
    @Provides
    fun bindSelectedCityRepository(binder: SelectedCityRepositoryBinder): SelectedCityRepository = binder()

    @FeatureScope
    @Provides
    fun bindSettingsRepository(binder: SettingsRepositoryBinder): SettingsRepository = binder()

    @FeatureScope
    @Provides
    fun bindForecastRepository(binder: ForecastRepositoryBinder): ForecastRepository = binder()
}
