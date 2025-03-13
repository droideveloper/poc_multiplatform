package com.multiplatform.td.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.byteArrayPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.mutablePreferencesOf
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.conflate

object InMemoryDataStoreFactory {

    fun create(
        dataStore: MutableMap<Preferences.Key<*>, Any> = mutableMapOf(),
    ) : DataStore<Preferences> = InMemoryDataStore(dataStore)
}

internal class InMemoryDataStore(
    private val dataStore: MutableMap<Preferences.Key<*>, Any>
) : DataStore<Preferences> {

    private val internalState = MutableSharedFlow<Preferences>(replay = 1)

    override val data: Flow<Preferences>
        get() = internalState.conflate()

    init {
        val preferences = mutablePreferencesOf()
        dataStore.forEach { (key, value) ->
            preferences[key] = value
        }
        internalState.tryEmit(preferences)
    }

    override suspend fun updateData(
        transform: suspend (t: Preferences) -> Preferences
    ): Preferences {
        val preferences = mutablePreferencesOf()
        dataStore.forEach { (key, value) ->
            preferences[key] = value
        }
        return transform(preferences).also {
            dataStore.clear()
            dataStore.putAll(it.asMap())
        }
    }

    private operator fun MutablePreferences.set(key: Preferences.Key<*>, value: Any) {
        when (value) {
            is Boolean -> this[booleanPreferencesKey(key.name)] = value
            is Int -> this[intPreferencesKey(key.name)] = value
            is Long -> this[longPreferencesKey(key.name)] = value
            is Float -> this[floatPreferencesKey(key.name)] = value
            is Double -> this[doublePreferencesKey(key.name)] = value
            is String -> this[stringPreferencesKey(key.name)] = value
            is ByteArray -> this[byteArrayPreferencesKey(key.name)] = value
            else -> throw IllegalArgumentException("Cannot set value for key `$key = $value`")
        }
    }
}
