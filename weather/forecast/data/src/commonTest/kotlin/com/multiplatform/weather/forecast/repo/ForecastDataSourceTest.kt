package com.multiplatform.weather.forecast.repo

import com.multiplatform.weather.city.City
import com.multiplatform.weather.forecast.service.ForecastService
import com.multiplatform.weather.forecast.service.forecastDto
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

internal class ForecastDataSourceTest {

    @Test
    fun `given success than will return forecast`() = runTest {
        val service = mock<ForecastService> {
            everySuspend {
                forecast(
                    latitude = Double.NaN,
                    longitude = Double.NaN,
                    days = 10,
                )
            } returns Result.success(forecastDto)
        }

        val dataSource = ForecastDataSource(service)

        val result = dataSource.get(City.Defaults)

        assertEquals(forecastDto, result.getOrThrow())

        verifySuspend {
            service.forecast(
                latitude = Double.NaN,
                longitude = Double.NaN,
                days = 10,
            )
        }
    }

    @Test
    fun `given http error than will return error`() = runTest {
        val error = Throwable()
        val service = mock<ForecastService> {
            everySuspend {
                forecast(
                    latitude = Double.NaN,
                    longitude = Double.NaN,
                    days = 10,
                )
            } returns Result.failure(error)
        }

        val dataSource = ForecastDataSource(service)

        val result = dataSource.get(City.Defaults)
        val actual = assertFails { result.getOrThrow() }

        assertEquals(error, actual)

        verifySuspend {
            service.forecast(
                latitude = Double.NaN,
                longitude = Double.NaN,
                days = 10,
            )
        }
    }
}
