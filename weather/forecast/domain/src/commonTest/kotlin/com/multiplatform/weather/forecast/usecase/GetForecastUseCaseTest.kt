package com.multiplatform.weather.forecast.usecase

import com.multiplatform.td.core.repository.Criteria
import com.multiplatform.td.core.repository.Repository
import com.multiplatform.weather.city.City
import com.multiplatform.weather.forecast.Forecast
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.time.Duration.Companion.days

internal class GetForecastUseCaseTest {

    @Test
    fun `given success will return data`() = runTest {
        val repository = mock<Repository<City, Forecast>> {
            everySuspend {
                query(City.Defaults, Criteria.ofTimed(2.days))
            } returns Result.success(Forecast.Defaults)
        }

        val useCase = GetForecastUseCase(repository)
        val result = useCase(City.Defaults)

        assertEquals(Forecast.Defaults, result.getOrThrow())
    }

    @Test
    fun `given failure will return error`() = runTest {
        val error = Throwable()
        val repository = mock<Repository<City, Forecast>> {
            everySuspend {
                query(City.Defaults, Criteria.ofTimed(2.days))
            } returns Result.failure(error)
        }

        val useCase = GetForecastUseCase(repository)
        val result = useCase(City.Defaults)

        val actual = assertFails { result.getOrThrow() }
        assertEquals(error, actual)
    }
}
