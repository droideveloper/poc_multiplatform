package com.multiplatform.weather.city.inject.binder

import com.multiplatform.td.core.injection.Binder
import com.multiplatform.weather.city.loader.JsonDataSourceProvider

expect class JsonDataSourceProviderBinder : Binder<JsonDataSourceProvider> {
    override fun invoke(): JsonDataSourceProvider
}
