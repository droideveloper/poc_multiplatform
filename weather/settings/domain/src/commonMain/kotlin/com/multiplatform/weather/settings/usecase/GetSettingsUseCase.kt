package com.multiplatform.weather.settings.usecase

import com.multiplatform.weather.settings.Settings
import com.multiplatform.weather.settings.repo.SettingsRepository
import me.tatarka.inject.annotations.Inject

@Inject
class GetSettingsUseCase(
    private val settingsRepository: SettingsRepository,
) {

    suspend operator fun invoke(): Result<Settings> = runCatching {
        settingsRepository.get().getOrThrow()
    }

    fun asFlow() = settingsRepository.asFlow()
}
