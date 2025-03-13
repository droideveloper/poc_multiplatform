package com.multiplatform.weather.city.inject.binder

import com.multiplatform.td.core.injection.Binder
import com.multiplatform.weather.city.db.CityDatabase
import com.multiplatform.weather.city.json.JsonDataLoaderImpl
import com.multiplatform.weather.city.loader.JsonDataLoader
import com.multiplatform.weather.city.loader.JsonDataSourceProvider
import me.tatarka.inject.annotations.Inject

@Inject
class JsonDataLoaderBinder(
    private val database: CityDatabase,
    private val jsonDataSourceProvider: JsonDataSourceProvider,
) : Binder<JsonDataLoader> {

    override fun invoke(): JsonDataLoader =
        JsonDataLoaderImpl(
            dao = database.cityDao(),
            sourceProvider = jsonDataSourceProvider,
        )
}