package com.multiplatform.todo.settings.repo

import com.multiplatform.todo.settings.Settings
import kotlinx.coroutines.flow.Flow

interface SettingRepository {

    suspend fun save(settings: Settings): Result<Unit>
    suspend fun get(): Result<Settings>

    fun asFlow(): Flow<Result<Settings>>
}