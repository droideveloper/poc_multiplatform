package com.multiplatform.weather.city.provider

import android.content.Context
import com.multiplatform.weather.city.loader.JsonDataSourceProvider
import okio.Source
import okio.source

internal class JsonDataSourceProviderImpl(
    private val context: Context
): JsonDataSourceProvider {

    companion object {
        private const val JsonPath = "composeResources/tdmultiplatform.weather.city.ui.generated.resources/files/cities.json"
    }

    override fun invoke(): Source  {
        return context.assets.open(JsonPath).source()
    }
}
