package com.multiplatform.weather.city.provider

import com.multiplatform.weather.city.loader.JsonDataSourceProvider
import com.multiplatform.weather.city.loader.JsonDataSourceProvider.Companion.JsonPath
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.Source
import platform.Foundation.NSBundle

internal class JsonDataSourceProviderImpl : JsonDataSourceProvider {

    override fun invoke(input: String): Source {
        val filePath = NSBundle.mainBundle.resourcePath + "/compose-resources/" + JsonPath + "/$input.json"
        return FileSystem.SYSTEM.source(file = filePath.toPath())
    }
}
