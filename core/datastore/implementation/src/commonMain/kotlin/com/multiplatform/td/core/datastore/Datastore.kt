package com.multiplatform.td.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.multiplatform.td.core.environment.Environment
import me.tatarka.inject.annotations.Inject

@Inject
class Datastore(
    private val environment: Environment,
    private val dataStorePreferencesFactory: DataStorePreferencesFactory,
) {

    fun create(): DataStore<Preferences> =
        when {
            environment.isMock -> InMemoryDataStoreFactory.create()
            else -> dataStorePreferencesFactory()
        }
}