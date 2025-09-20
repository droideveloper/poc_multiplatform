package com.multiplatform.todo.settings.usecase

import com.multiplatform.todo.settings.Settings
import com.multiplatform.todo.settings.repo.SettingRepository
import me.tatarka.inject.annotations.Inject

@Inject
class GetSettingsUseCase(
    private val repository: SettingRepository,
) {

    suspend operator fun invoke(): Result<Settings> = runCatching {
        repository.get()
            .getOrThrow()
    }
}
