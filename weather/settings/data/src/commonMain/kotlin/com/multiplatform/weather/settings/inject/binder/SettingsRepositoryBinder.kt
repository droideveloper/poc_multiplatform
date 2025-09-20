package com.multiplatform.weather.settings.inject.binder

import com.multiplatform.td.core.datastore.KeyedValueDataStore
import com.multiplatform.td.core.injection.Binder
import com.multiplatform.weather.settings.repo.SettingsRepository
import com.multiplatform.weather.settings.repo.SettingsRepositoryImpl
import me.tatarka.inject.annotations.Inject

@Inject
class SettingsRepositoryBinder(
    private val dataStore: KeyedValueDataStore,
) : Binder<SettingsRepository> {

    override fun invoke(): SettingsRepository =
        SettingsRepositoryImpl(dataStore = dataStore)
}
