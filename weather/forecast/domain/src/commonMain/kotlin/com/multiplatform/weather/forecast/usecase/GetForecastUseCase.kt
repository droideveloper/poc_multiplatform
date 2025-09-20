package com.multiplatform.weather.forecast.usecase

import com.multiplatform.td.core.repository.Criteria
import com.multiplatform.weather.city.City
import com.multiplatform.weather.forecast.Forecast
import com.multiplatform.weather.forecast.repo.ForecastRepository
import me.tatarka.inject.annotations.Inject
import kotlin.time.Duration.Companion.days

@Inject
class GetForecastUseCase(
    private val forecastRepository: ForecastRepository,
) {

    // will invalidate cache probably max (10) days or min (2) days in (2) days
    private val criteria by lazy { Criteria.ofTimed(2.days) }

    suspend operator fun invoke(city: City): Result<Forecast> = runCatching {
        forecastRepository.query(city, criteria)
            .getOrThrow()
    }
}
