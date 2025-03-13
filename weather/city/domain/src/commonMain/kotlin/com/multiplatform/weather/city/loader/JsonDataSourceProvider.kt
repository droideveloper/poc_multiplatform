package com.multiplatform.weather.city.loader

import okio.Source

interface JsonDataSourceProvider {

    operator fun invoke(): Source
}