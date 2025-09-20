package com.multiplatform.weather.forecast.repo

import com.multiplatform.td.core.repository.DataSource
import com.multiplatform.weather.city.City
import com.multiplatform.weather.forecast.ForecastDto
import com.multiplatform.weather.forecast.service.ForecastService

internal class ForecastDataSource(
    private val service: ForecastService,
) : DataSource<City, ForecastDto> {

    override suspend fun get(argument: City): Result<ForecastDto> = runCatching {
        val (latitude, longitude) = argument.location
        service.forecast(
            latitude = latitude,
            longitude = longitude,
        )
            .getOrThrow()
    }
}
