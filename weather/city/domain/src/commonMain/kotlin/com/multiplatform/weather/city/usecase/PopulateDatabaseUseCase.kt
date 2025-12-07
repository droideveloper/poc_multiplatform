package com.multiplatform.weather.city.usecase

import com.multiplatform.weather.city.CountryCode
import com.multiplatform.weather.city.loader.JsonCityDataLoader
import me.tatarka.inject.annotations.Inject

@Inject
class PopulateDatabaseUseCase(
    private val jsonCityDataLoader: JsonCityDataLoader,
) {

    suspend operator fun invoke(countryCode: CountryCode): Result<Unit> = runCatching {
        jsonCityDataLoader(countryCode).getOrThrow()
    }.onFailure { it.printStackTrace() }
}
