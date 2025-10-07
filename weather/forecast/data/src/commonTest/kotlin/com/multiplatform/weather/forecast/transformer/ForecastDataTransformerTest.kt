package com.multiplatform.weather.forecast.transformer

import com.multiplatform.weather.forecast.service.forecastDto
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class ForecastDataTransformerTest {

    @Test
    fun `given dto than will return domain data`() {
        val transformer = ForecastDataTransformer()

        val result = transformer.transform(forecastDto)

        assertTrue { result.weather.keys.size == result.average.size }
        assertFalse { result.weather.values.any { it.size != 24 } }
    }
}
