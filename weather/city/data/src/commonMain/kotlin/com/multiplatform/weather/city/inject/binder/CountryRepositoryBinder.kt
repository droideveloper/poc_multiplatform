package com.multiplatform.weather.city.inject.binder

import com.multiplatform.td.core.injection.Binder
import com.multiplatform.weather.city.loader.JsonCountryDataLoader
import com.multiplatform.weather.city.repo.CountryRepository
import com.multiplatform.weather.city.repo.CountryRepositoryImpl
import me.tatarka.inject.annotations.Inject

@Inject
class CountryRepositoryBinder(
    private val jsonCountryDataLoader: JsonCountryDataLoader,
) : Binder<CountryRepository> {

    override fun invoke(): CountryRepository =
        CountryRepositoryImpl(
            jsonCountryDataLoader = jsonCountryDataLoader,
        )
}
