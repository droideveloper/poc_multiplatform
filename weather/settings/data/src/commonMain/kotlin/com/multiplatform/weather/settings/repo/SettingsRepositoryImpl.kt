package com.multiplatform.weather.settings.repo

import com.multiplatform.td.core.datastore.DataStoreKey
import com.multiplatform.td.core.datastore.KeyedValueDataStore
import com.multiplatform.td.core.datastore.getSerializable
import com.multiplatform.td.core.datastore.getSerializableFlow
import com.multiplatform.td.core.datastore.setSerializable
import com.multiplatform.weather.settings.json.JsonSettings
import com.multiplatform.weather.settings.Settings
import com.multiplatform.weather.settings.json.toData
import com.multiplatform.weather.settings.json.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class SettingsRepositoryImpl(
    private val dataStore: KeyedValueDataStore,
) : SettingsRepository {

    private val dataStoreKey by lazy { DataStoreKey.defaultStoreKey("settings") }

    override suspend fun save(settings: Settings): Result<Unit> = runCatching {
        dataStore.setSerializable(
            dataStoreKey,
            settings.toData()
        )
            .getOrThrow()
    }

    override suspend fun get(): Result<Settings> = runCatching {
        val result = dataStore.getSerializable<JsonSettings>(
            dataStoreKey,
        )
            .getOrThrow()
        // map to domain
        result.toDomain()
    }

    override fun asFlow(): Flow<Result<Settings>> = dataStore.getSerializableFlow<JsonSettings>(
        dataStoreKey,
    )
        .map { result ->
            result.fold(
                onSuccess = { Result.success(it.toDomain()) },
                onFailure = { Result.failure(it) }
            )
        }
}