package com.multiplatform.td.core.datastore

import androidx.datastore.core.DataMigration
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.CoroutineScope
import okio.Path.Companion.toPath

data class DataStoreArgs(
    val name: Lazy<String>,
    val scope: CoroutineScope,
    val migrations: List<DataMigration<Preferences>> = listOf(),
)

internal fun createDataStore(args: DataStoreArgs): DataStore<Preferences> =
    PreferenceDataStoreFactory.createWithPath(
        migrations = args.migrations,
        scope = args.scope,
        produceFile = { args.name.value.toPath() },
    )
