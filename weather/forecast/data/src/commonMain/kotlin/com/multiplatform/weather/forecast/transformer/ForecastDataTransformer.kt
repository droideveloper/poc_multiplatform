package com.multiplatform.weather.forecast.transformer

import com.multiplatform.td.core.repository.DataTransformer
import com.multiplatform.weather.forecast.Forecast
import com.multiplatform.weather.forecast.ForecastDto
import com.multiplatform.weather.forecast.toDomain

internal class ForecastDataTransformer : DataTransformer<ForecastDto, Forecast> {
    override fun transform(input: ForecastDto): Forecast = input.toDomain()
}
