package com.multiplatform.weather.city.inject

import com.multiiplatform.td.core.database.Database
import com.multiplatform.td.core.datastore.KeyedValueDataStore
import com.multiplatform.td.core.environment.Environment
import com.multiplatform.td.core.injection.scopes.FeatureScope
import com.multiplatform.weather.city.db.CityDatabase
import com.multiplatform.weather.city.inject.binder.CityRepositoryBinder
import com.multiplatform.weather.city.inject.binder.JsonDataLoaderBinder
import com.multiplatform.weather.city.inject.binder.SelectedCityRepositoryBinder
import com.multiplatform.weather.city.loader.JsonDataLoader
import com.multiplatform.weather.city.repo.CityRepository
import com.multiplatform.weather.city.repo.SelectedCityRepository
import me.tatarka.inject.annotations.Provides

interface CityDataModule {
    val db: CityDatabase
    val dataStore: KeyedValueDataStore
    val environment: Environment
    val database: Database

    val jsonDataLoader: JsonDataLoader
    val cityRepository: CityRepository
    val selectedCityRepository: SelectedCityRepository

    @FeatureScope
    @Provides
    fun bindJsonDataLoader(binder: JsonDataLoaderBinder): JsonDataLoader = binder()

    @FeatureScope
    @Provides
    fun bindCityRepository(binder: CityRepositoryBinder): CityRepository = binder()

    @FeatureScope
    @Provides
    fun bindSelectedCityRepository(binder: SelectedCityRepositoryBinder): SelectedCityRepository = binder()
}
