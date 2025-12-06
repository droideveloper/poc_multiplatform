package com.multiplatform.weather.city.loader

import com.multiplatform.weather.city.Country
import com.multiplatform.weather.city.CountryCode

interface JsonDataLoader<T, R> {

    suspend operator fun invoke(input: T): Result<R>
}

interface JsonCityDataLoader : JsonDataLoader<CountryCode, Unit>
interface JsonCountryDataLoader : JsonDataLoader<String, List<Country>>
