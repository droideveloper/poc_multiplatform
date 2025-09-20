package com.multiplatform.weather.city.inject.binder

import com.multiplatform.td.core.injection.Binder
import com.multiplatform.weather.city.loader.JsonDataSourceProvider
import com.multiplatform.weather.city.provider.JsonDataSourceProviderImpl
import me.tatarka.inject.annotations.Inject

@Inject
actual class JsonDataSourceProviderBinder : Binder<JsonDataSourceProvider> {

    actual override fun invoke(): JsonDataSourceProvider =
        JsonDataSourceProviderImpl()
}
