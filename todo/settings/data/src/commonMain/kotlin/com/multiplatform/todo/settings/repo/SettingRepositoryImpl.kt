package com.multiplatform.todo.settings.repo

import com.multiplatform.td.core.datastore.DataStoreKey
import com.multiplatform.td.core.datastore.KeyedValueDataStore
import com.multiplatform.td.core.datastore.getSerializable
import com.multiplatform.td.core.datastore.getSerializableFlow
import com.multiplatform.td.core.datastore.setSerializable
import com.multiplatform.td.core.injection.binding.ContributesBinder
import com.multiplatform.td.core.injection.scopes.FeatureScope
import com.multiplatform.todo.settings.Settings
import com.multiplatform.todo.settings.json.JsonSettings
import com.multiplatform.todo.settings.mapper.toData
import com.multiplatform.todo.settings.mapper.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@ContributesBinder(FeatureScope::class)
internal class SettingRepositoryImpl(
    private val dataStore: KeyedValueDataStore,
) : SettingRepository {

    private val dataStoreKey = DataStoreKey.defaultStoreKey("settings")

    override suspend fun save(settings: Settings): Result<Unit> = runCatching {
        dataStore.setSerializable(
            dataStoreKey,
            settings.toData(),
        )
            .getOrThrow()
    }

    override suspend fun get(): Result<Settings> = runCatching {
        val result = dataStore.getSerializable<JsonSettings>(
            dataStoreKey,
        )
            .getOrThrow()

        result.toDomain()
    }

    override fun asFlow(): Flow<Result<Settings>> = dataStore.getSerializableFlow<JsonSettings>(dataStoreKey)
        .map { result ->
            result.fold(
                onSuccess = { Result.success(it.toDomain()) },
                onFailure = { Result.failure(it) },
            )
        }
}
