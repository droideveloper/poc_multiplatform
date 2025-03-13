package com.multiplatform.todo.settings.usecase

import com.multiplatform.todo.settings.Settings
import com.multiplatform.todo.settings.repo.SettingRepository
import me.tatarka.inject.annotations.Inject

@Inject
class SaveSettingsUseCase(
    private val repository: SettingRepository,
) {

    suspend operator fun invoke(settings: Settings): Result<Unit> = runCatching {
        repository.save(settings)
            .getOrThrow()
    }
}