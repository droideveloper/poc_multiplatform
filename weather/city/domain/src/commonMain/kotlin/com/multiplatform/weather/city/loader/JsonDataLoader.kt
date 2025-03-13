package com.multiplatform.weather.city.loader

interface JsonDataLoader {

    suspend operator fun invoke(): Result<Unit>
}
