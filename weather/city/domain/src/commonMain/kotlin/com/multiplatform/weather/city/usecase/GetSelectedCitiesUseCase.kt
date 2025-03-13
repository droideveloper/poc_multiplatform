package com.multiplatform.weather.city.usecase

import com.multiplatform.weather.city.City
import com.multiplatform.weather.city.repo.SelectedCityRepository
import kotlinx.coroutines.flow.Flow
import me.tatarka.inject.annotations.Inject

@Inject
class GetSelectedCitiesUseCase(
    private val selectedCityRepository: SelectedCityRepository,
) {

    suspend operator fun invoke(): Result<List<City>> = runCatching {
        selectedCityRepository.cities()
            .getOrThrow()
    }

    fun asFlow(): Flow<Result<List<City>>> = selectedCityRepository.asFlow()
}