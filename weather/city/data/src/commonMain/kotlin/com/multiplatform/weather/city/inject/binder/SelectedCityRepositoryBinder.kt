package com.multiplatform.weather.city.inject.binder

import com.multiplatform.td.core.datastore.KeyedValueDataStore
import com.multiplatform.td.core.injection.Binder
import com.multiplatform.weather.city.repo.SelectedCityRepository
import com.multiplatform.weather.city.repo.SelectedCityRepositoryImpl
import me.tatarka.inject.annotations.Inject

@Inject
class SelectedCityRepositoryBinder(
    private val dataStore: KeyedValueDataStore,
) : Binder<SelectedCityRepository> {

    override fun invoke(): SelectedCityRepository =
        SelectedCityRepositoryImpl(
            dataStore = dataStore
        )
}