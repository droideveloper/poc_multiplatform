package com.multiplatform.weather.city.inject

import com.multiiplatform.td.core.database.Database
import com.multiplatform.td.core.datastore.KeyedValueDataStore
import com.multiplatform.td.core.environment.Environment
import com.multiplatform.td.core.injection.scopes.FeatureScope
import com.multiplatform.weather.city.db.CityDatabase
import com.multiplatform.weather.city.inject.binder.CityRepositoryBinder
import com.multiplatform.weather.city.inject.binder.CountryRepositoryBinder
import com.multiplatform.weather.city.inject.binder.JsonCityDataLoaderBinder
import com.multiplatform.weather.city.inject.binder.JsonCountryDataLoaderBinder
import com.multiplatform.weather.city.inject.binder.SelectedCityRepositoryBinder
import com.multiplatform.weather.city.loader.JsonCityDataLoader
import com.multiplatform.weather.city.loader.JsonCountryDataLoader
import com.multiplatform.weather.city.repo.CityRepository
import com.multiplatform.weather.city.repo.CountryRepository
import com.multiplatform.weather.city.repo.SelectedCityRepository
import me.tatarka.inject.annotations.Provides

interface CityDataModule {
    val db: CityDatabase
    val dataStore: KeyedValueDataStore
    val environment: Environment
    val database: Database
    val jsonCityDataLoader: JsonCityDataLoader
    val jsonCountryDataLoader: JsonCountryDataLoader
    val cityRepository: CityRepository
    val countryRepository: CountryRepository
    val selectedCityRepository: SelectedCityRepository

    @FeatureScope
    @Provides
    fun bindJsonCityDataLoader(binder: JsonCityDataLoaderBinder): JsonCityDataLoader = binder()

    @FeatureScope
    @Provides
    fun bindJsonCountryDataLoader(binder: JsonCountryDataLoaderBinder): JsonCountryDataLoader = binder()

    @FeatureScope
    @Provides
    fun bindCityRepository(binder: CityRepositoryBinder): CityRepository = binder()

    @FeatureScope
    @Provides
    fun bindCountryRepository(binder: CountryRepositoryBinder): CountryRepository = binder()

    @FeatureScope
    @Provides
    fun bindSelectedCityRepository(binder: SelectedCityRepositoryBinder): SelectedCityRepository = binder()
}
