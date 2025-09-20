package com.multiplatform.weather.city.usecase

import com.multiplatform.weather.city.City
import com.multiplatform.weather.city.repo.SelectedCityRepository
import me.tatarka.inject.annotations.Inject

@Inject
class SaveCityUseCase(
    private val selectedCityRepository: SelectedCityRepository,
) {

    suspend operator fun invoke(city: City): Result<Unit> = runCatching {
        val result = selectedCityRepository.cities()
        val cities = (result.getOrNull() ?: emptyList()) + city
        selectedCityRepository.save(cities)
            .getOrThrow()
    }
}
