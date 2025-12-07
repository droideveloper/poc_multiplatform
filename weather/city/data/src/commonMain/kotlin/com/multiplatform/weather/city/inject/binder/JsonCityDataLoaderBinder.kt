package com.multiplatform.weather.city.inject.binder

import com.multiplatform.td.core.injection.Binder
import com.multiplatform.weather.city.db.CityDatabase
import com.multiplatform.weather.city.json.JsonCityDataLoaderImpl
import com.multiplatform.weather.city.loader.JsonCityDataLoader
import com.multiplatform.weather.city.loader.JsonDataSourceProvider
import me.tatarka.inject.annotations.Inject

@Inject
class JsonCityDataLoaderBinder(
    private val database: CityDatabase,
    private val jsonDataSourceProvider: JsonDataSourceProvider,
) : Binder<JsonCityDataLoader> {

    override fun invoke(): JsonCityDataLoader =
        JsonCityDataLoaderImpl(
            dao = database.cityDao(),
            sourceProvider = jsonDataSourceProvider,
        )
}
