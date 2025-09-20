package com.multiplatform.weather.city.usecase

import com.multiplatform.weather.city.loader.JsonDataLoader
import me.tatarka.inject.annotations.Inject

@Inject
class PopulateDatabaseUseCase(
    private val jsonDataLoader: JsonDataLoader,
) {

    suspend operator fun invoke(): Result<Unit> = runCatching {
        jsonDataLoader().getOrThrow()
    }
}
