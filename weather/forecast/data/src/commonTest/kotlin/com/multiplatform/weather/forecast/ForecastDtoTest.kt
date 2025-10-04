package com.multiplatform.weather.forecast

import com.multiplatform.weather.forecast.service.averageDto
import com.multiplatform.weather.forecast.service.weatherDto
import kotlin.test.Test
import kotlin.test.assertTrue

internal class ForecastDtoTest {

    @Test
    fun `given forecast dto than will return forecast`() {
        val actual = ForecastDto(
            average = averageDto,
            weather = weatherDto,
        )
            .toDomain()

        actual.weather.keys.forEach { key ->
            val weather = actual.weather.getValue(key)

            assertTrue { weather.size == 24 }
        }

        assertTrue { actual.average.size == 3 }
    }
}
