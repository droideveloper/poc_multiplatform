package com.multiplatform.td.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

interface DataStorePreferencesFactory {

    operator fun invoke(): DataStore<Preferences>
}
