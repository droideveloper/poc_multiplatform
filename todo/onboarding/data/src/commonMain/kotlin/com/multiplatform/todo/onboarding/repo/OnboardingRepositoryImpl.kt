package com.multiplatform.todo.onboarding.repo

import com.multiplatform.td.core.datastore.DataStoreKey
import com.multiplatform.td.core.datastore.KeyedValueDataStore
import com.multiplatform.td.core.datastore.getSerializable
import com.multiplatform.td.core.datastore.setSerializable
import com.multiplatform.td.core.injection.binding.ContributesBinder
import com.multiplatform.td.core.injection.scopes.FeatureScope
import com.multiplatform.todo.onboarding.JsonOnboarding
import com.multiplatform.todo.onboarding.Onboarding
import com.multiplatform.todo.onboarding.mapper.toData
import com.multiplatform.todo.onboarding.mapper.toDomain

@ContributesBinder(scope = FeatureScope::class)
internal class OnboardingRepositoryImpl(
    private val dataStore: KeyedValueDataStore,
) : OnboardingRepository {

    private val dataStoreKey get() = DataStoreKey.defaultStoreKey("onboarding")

    override suspend fun save(onboarding: Onboarding): Result<Unit> = runCatching {
        val result = dataStore.setSerializable(
            key = dataStoreKey,
            value = onboarding.toData(),
        )

        result.getOrThrow()
    }

    override suspend fun get(): Result<Onboarding> = runCatching {
        val result = dataStore.getSerializable<JsonOnboarding>(
            key = dataStoreKey,
        )

        result
            .getOrThrow()
            .toDomain()
    }
}
