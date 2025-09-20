package com.multiplatform.weather.city.inject.binder

import android.content.Context
import com.multiplatform.td.core.injection.Binder
import com.multiplatform.weather.city.loader.JsonDataSourceProvider
import com.multiplatform.weather.city.provider.JsonDataSourceProviderImpl
import me.tatarka.inject.annotations.Inject

@Inject
actual class JsonDataSourceProviderBinder(
    private val context: Context,
) : Binder<JsonDataSourceProvider> {

    actual override fun invoke(): JsonDataSourceProvider =
        JsonDataSourceProviderImpl(
            context = context,
        )
}
