package com.multiplatform.weather.city.repo

import com.multiplatform.weather.city.Country
import com.multiplatform.weather.city.loader.JsonCountryDataLoader

internal class CountryRepositoryImpl(
    private val jsonCountryDataLoader: JsonCountryDataLoader,
) : CountryRepository {

    companion object {
        private const val FileName = "countries"
    }

    override suspend fun countries(): Result<List<Country>> = runCatching {
        jsonCountryDataLoader(input = FileName).getOrThrow()
    }
}
