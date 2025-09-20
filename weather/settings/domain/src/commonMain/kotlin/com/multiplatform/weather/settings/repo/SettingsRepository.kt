package com.multiplatform.weather.settings.repo

import com.multiplatform.weather.settings.Settings
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    suspend fun save(settings: Settings): Result<Unit>
    suspend fun get(): Result<Settings>
    fun asFlow(): Flow<Result<Settings>>
}
