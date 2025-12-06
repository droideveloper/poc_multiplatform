package com.multiplatform.weather.city.provider

import android.content.Context
import com.multiplatform.weather.city.loader.JsonDataSourceProvider
import com.multiplatform.weather.city.loader.JsonDataSourceProvider.Companion.JsonPath
import okio.Source
import okio.source

internal class JsonDataSourceProviderImpl(
    private val context: Context,
) : JsonDataSourceProvider {

    override fun invoke(input: String): Source {
        return context.assets.open("$JsonPath/$input.json").source()
    }
}
