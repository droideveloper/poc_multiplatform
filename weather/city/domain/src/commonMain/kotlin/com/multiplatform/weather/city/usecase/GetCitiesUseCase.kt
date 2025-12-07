package com.multiplatform.weather.city.usecase

import com.multiplatform.weather.city.City
import com.multiplatform.weather.city.CountryCode
import com.multiplatform.weather.city.repo.CityRepository
import me.tatarka.inject.annotations.Inject

@Inject
class GetCitiesUseCase(
    private val cityRepository: CityRepository,
) {

    suspend operator fun invoke(countryCode: CountryCode): Result<List<City>> = runCatching {
        cityRepository.cities(countryCode)
            .getOrThrow()
    }
}
