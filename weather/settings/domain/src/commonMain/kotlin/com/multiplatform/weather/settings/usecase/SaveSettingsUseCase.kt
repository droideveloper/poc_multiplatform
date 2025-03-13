package com.multiplatform.weather.settings.usecase

import com.multiplatform.weather.settings.Settings
import com.multiplatform.weather.settings.repo.SettingsRepository
import me.tatarka.inject.annotations.Inject

@Inject
class SaveSettingsUseCase(
    private val settingsRepository: SettingsRepository,
) {

    suspend operator fun invoke(settings: Settings): Result<Unit> = runCatching {
        settingsRepository.save(settings)
            .getOrThrow()
    }
}