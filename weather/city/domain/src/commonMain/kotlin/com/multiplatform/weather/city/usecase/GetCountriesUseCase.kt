package com.multiplatform.weather.city.usecase

import com.multiplatform.weather.city.Country
import com.multiplatform.weather.city.repo.CountryRepository
import me.tatarka.inject.annotations.Inject

@Inject
class GetCountriesUseCase(
    private val countryRepository: CountryRepository,
) {

    suspend operator fun invoke(): Result<List<Country>> = runCatching {
        countryRepository.countries()
            .getOrThrow()
    }
}
