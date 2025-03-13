package com.multiplatform.weather.onboarding.repo

import com.multiplatform.td.core.datastore.DataStoreKey
import com.multiplatform.td.core.datastore.KeyedValueDataStore

internal class OnboardingRepositoryImpl(
    private val dataStore: KeyedValueDataStore,
) : OnboardingRepository {

    private val dataStoreKey by lazy { DataStoreKey.defaultStoreKey("onboarding") }

    override suspend fun save(isCompleted: Boolean): Result<Unit> = runCatching {
        dataStore.setValue(dataStoreKey, isCompleted)
            .getOrThrow()
    }

    override suspend fun get(): Result<Boolean> = runCatching {
        dataStore.getValue(dataStoreKey, Boolean::class)
            .getOrThrow()
    }
}