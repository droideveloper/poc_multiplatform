package com.multiplatform.weather.forecast.repo

import com.multiplatform.td.core.datastore.KeyedValueDataStore
import com.multiplatform.td.core.repository.DataSource
import com.multiplatform.td.core.repository.DataTransformer
import com.multiplatform.td.core.repository.createRepository
import com.multiplatform.weather.city.City
import com.multiplatform.weather.forecast.Forecast
import com.multiplatform.weather.forecast.ForecastDto
import com.multiplatform.weather.forecast.cache.ForecastDataStoreCache

internal class ForecastRepositoryImpl(
    dataStore: KeyedValueDataStore,
    dataSource: DataSource<City, ForecastDto>,
    transformer: DataTransformer<ForecastDto, Forecast>,
) : ForecastRepository by createRepository(
    cacheFactory = { arg ->
        val key = "forecast_${arg.createKey()}"
        ForecastDataStoreCache(
            dataStore = dataStore,
            key = key,
        )
    },
    dataSource = dataSource,
    dataTransformer = transformer,
)
