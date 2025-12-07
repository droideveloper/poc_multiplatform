package com.multiplatform.weather.city.repo

import com.multiplatform.weather.city.Country

interface CountryRepository {
    suspend fun countries(): Result<List<Country>>
}
