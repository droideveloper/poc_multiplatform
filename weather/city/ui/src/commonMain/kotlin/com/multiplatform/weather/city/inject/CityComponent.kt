package com.multiplatform.weather.city.inject

import com.multiiplatform.td.core.database.inject.DatabaseComponent
import com.multiplatform.td.core.datastore.inject.DataStoreComponent
import com.multiplatform.td.core.injection.scopes.FeatureScope
import com.multiplatform.weather.city.GeneratedViewModelModule
import com.multiplatform.weather.city.db.CityDatabase
import com.multiplatform.weather.city.inject.binder.CityDatabaseBinder
import com.multiplatform.weather.city.inject.binder.JsonDataSourceProviderBinder
import com.multiplatform.weather.city.loader.JsonDataSourceProvider
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.KmpComponentCreate
import me.tatarka.inject.annotations.Provides

@FeatureScope
@Component
abstract class CityComponent(
    @Component val databaseComponent: DatabaseComponent,
    @Component val dataStoreComponent: DataStoreComponent,
) : GeneratedViewModelModule(), CityDataModule {
    companion object;

    @FeatureScope
    @Provides
    fun bindJsonDataSourceProvider(binder: JsonDataSourceProviderBinder): JsonDataSourceProvider = binder()

    @FeatureScope
    @Provides
    fun bindCityDatabase(binder: CityDatabaseBinder): CityDatabase = binder()
}

@KmpComponentCreate
expect fun createCityComponent(
    databaseComponent: DatabaseComponent,
    dataStoreComponent: DataStoreComponent,
): CityComponent
