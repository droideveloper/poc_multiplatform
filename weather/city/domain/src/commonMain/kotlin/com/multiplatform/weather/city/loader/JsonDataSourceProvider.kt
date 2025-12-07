package com.multiplatform.weather.city.loader

import okio.Source

interface JsonDataSourceProvider {

    companion object {
        const val JsonPath = "composeResources/tdmultiplatform.weather.city.ui.generated.resources/files"
    }

    operator fun invoke(input: String): Source
}
