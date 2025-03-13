package com.multiplatform.weather.city.provider

import com.multiplatform.weather.city.loader.JsonDataSourceProvider
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.Source
import platform.Foundation.NSBundle

internal class JsonDataSourceProviderImpl : JsonDataSourceProvider {

    companion object {
        private const val JsonPath = "/compose-resources/composeResources/tdmultiplatform.weather.city.ui.generated.resources/files/cities.json"
    }

    override fun invoke(): Source {
        val filePath = NSBundle.mainBundle.resourcePath + JsonPath
        return FileSystem.SYSTEM.source(file = filePath.toPath())
    }
}
