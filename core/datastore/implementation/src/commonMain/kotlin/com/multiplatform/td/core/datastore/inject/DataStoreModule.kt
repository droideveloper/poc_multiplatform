package com.multiplatform.td.core.datastore.inject

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.multiplatform.td.core.datastore.CoroutinesScopeFactory
import com.multiplatform.td.core.datastore.DataStoreArgs
import com.multiplatform.td.core.datastore.DataStoreArgsFactory
import com.multiplatform.td.core.datastore.DataStorePreferencesFactory
import com.multiplatform.td.core.datastore.GeneratedBinderModule
import com.multiplatform.td.core.datastore.KeyedValueDataStore
import com.multiplatform.td.core.datastore.Serializer
import com.multiplatform.td.core.datastore.inject.binder.DataStoreArgsFactoryBinder
import com.multiplatform.td.core.datastore.inject.binder.KeyedValueDataStoreBinder
import com.multiplatform.td.core.injection.scopes.DataStoreScope
import kotlinx.coroutines.CoroutineScope
import me.tatarka.inject.annotations.Provides

interface DataStoreModule : GeneratedBinderModule {

    val coroutineScope: CoroutineScope
    val serializer: Serializer
    val coroutinesScopeFactory: CoroutinesScopeFactory

    @DataStoreScope
    @Provides
    fun dataStoreArgsFactory(factory: DataStoreArgsFactory): DataStoreArgs = factory()

    @DataStoreScope
    @Provides
    fun coroutinesScopeFactory(factory: CoroutinesScopeFactory): CoroutineScope = factory()

    @DataStoreScope
    @Provides
    fun dataStorePreferencesFactory(factory: DataStorePreferencesFactory): DataStore<Preferences> = factory()

    @DataStoreScope
    @Provides
    fun bindDataStoreArgsFactory(binder: DataStoreArgsFactoryBinder): DataStoreArgsFactory = binder()

    @DataStoreScope
    @Provides
    fun bindKeyedValueDataStore(binder: KeyedValueDataStoreBinder): KeyedValueDataStore = binder()
}
