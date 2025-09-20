package com.multiplatform.td.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.multiplatform.td.core.injection.binding.ContributesBinder
import com.multiplatform.td.core.injection.scopes.DataStoreScope

@ContributesBinder(scope = DataStoreScope::class)
internal class DataStorePreferencesFactoryImpl(
    private val dataStoreArgsFactory: DataStoreArgsFactory,
) : DataStorePreferencesFactory {

    override fun invoke(): DataStore<Preferences> =
        createDataStore(
            args = dataStoreArgsFactory(),
        )
}
