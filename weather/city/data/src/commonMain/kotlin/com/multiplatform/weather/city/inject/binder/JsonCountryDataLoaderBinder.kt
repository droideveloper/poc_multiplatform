package com.multiplatform.weather.city.inject.binder

import com.multiplatform.td.core.injection.Binder
import com.multiplatform.weather.city.json.JsonCountryDataLoaderImpl
import com.multiplatform.weather.city.loader.JsonCountryDataLoader
import com.multiplatform.weather.city.loader.JsonDataSourceProvider
import me.tatarka.inject.annotations.Inject

@Inject
class JsonCountryDataLoaderBinder(
    private val jsonDataSourceProvider: JsonDataSourceProvider,
) : Binder<JsonCountryDataLoader> {

    override fun invoke(): JsonCountryDataLoader =
        JsonCountryDataLoaderImpl(
            sourceProvider = jsonDataSourceProvider,
        )
}
