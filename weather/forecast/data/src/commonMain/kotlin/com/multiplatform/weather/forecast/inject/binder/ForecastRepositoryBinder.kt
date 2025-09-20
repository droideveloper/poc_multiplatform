package com.multiplatform.weather.forecast.inject.binder

import com.multiplatform.td.core.datastore.KeyedValueDataStore
import com.multiplatform.td.core.injection.Binder
import com.multiplatform.td.core.network.Network
import com.multiplatform.td.core.repository.DataSource
import com.multiplatform.td.core.repository.DataTransformer
import com.multiplatform.weather.city.City
import com.multiplatform.weather.forecast.Forecast
import com.multiplatform.weather.forecast.ForecastDto
import com.multiplatform.weather.forecast.repo.ForecastDataSource
import com.multiplatform.weather.forecast.repo.ForecastRepository
import com.multiplatform.weather.forecast.repo.ForecastRepositoryImpl
import com.multiplatform.weather.forecast.service.MockService
import com.multiplatform.weather.forecast.service.createForecastService
import com.multiplatform.weather.forecast.transformer.ForecastDataTransformer
import me.tatarka.inject.annotations.Inject

@Inject
class ForecastRepositoryBinder(
    private val dataStore: KeyedValueDataStore,
    private val network: Network,
) : Binder<ForecastRepository> {

    override fun invoke(): ForecastRepository =
        ForecastRepositoryImpl(
            dataStore = dataStore,
            dataSource = createDataSource(),
            transformer = createDataTransformer(),
        )

    private fun createDataSource(): DataSource<City, ForecastDto> =
        ForecastDataSource(
            service = network.create(
                serviceFactory = { createForecastService() },
                mockServiceFactory = { MockService.create() },
            ),
        )

    private fun createDataTransformer(): DataTransformer<ForecastDto, Forecast> =
        ForecastDataTransformer()
}
