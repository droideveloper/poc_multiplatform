package com.multiplatform.td.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.byteArrayPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlin.reflect.KClass
import kotlin.reflect.KType

internal class KeyedValueDataStoreImpl(
    private val dataStore: Lazy<DataStore<Preferences>>,
    private val serializer: Serializer,
) : KeyedValueDataStore {

    override suspend fun <T> setValue(key: Result<DataStoreKey>, value: T): Result<Unit> =
        runCatching {
            val k = key.getOrThrow()
            dataStore.value.edit {
                it[k.storeKey] = value
            }
        }

    override suspend fun <T : Any> getValue(
        key: Result<DataStoreKey>,
        valueClass: KClass<T>,
    ): Result<T> = runCatching {
        val k = key.getOrThrow()
        getResult(k.storeKey) {
            get(k.storeKey, valueClass)
                ?: throw KeyedValueDataStoreException.NotFoundException(k.storeKey)
        }
    }

    override fun <T : Any> getValueFlow(
        key: Result<DataStoreKey>,
        valueClass: KClass<T>,
    ): Flow<Result<T>> {
        val k = key.getOrThrow()
        return getResultFlow(k.storeKey) {
            get(k.storeKey, valueClass)
                ?: throw KeyedValueDataStoreException.NotFoundException(k.storeKey)
        }
            .map { Result.success(it) }
            .catch { Result.failure<T>(it) }
    }

    override suspend fun <T> setSerializable(
        key: Result<DataStoreKey>,
        valueType: KType,
        value: T,
    ): Result<Unit> = runCatching {
        val k = key.getOrThrow()
        dataStore.value.edit { it[k.storeKey, valueType] = value }
    }

    override suspend fun <T : Any> getSerializable(
        key: Result<DataStoreKey>,
        valueType: KType,
    ): Result<T> = runCatching {
        val k = key.getOrThrow()
        getResult(k.storeKey) {
            get<T>(k.storeKey, valueType)
                ?: throw KeyedValueDataStoreException.NotFoundException(k.storeKey)
        }
    }

    override fun <T : Any> getSerializableFlow(
        key: Result<DataStoreKey>,
        valueType: KType,
    ): Flow<Result<T>> {
        val k = key.getOrThrow()
        return getResultFlow(k.storeKey) {
            get<T>(k.storeKey, valueType)
                ?: throw KeyedValueDataStoreException.NotFoundException(k.storeKey)
        }
            .map { Result.success(it) }
            .catch { Result.failure<T>(it) }
    }

    override suspend fun resetDataStore(dataStoreName: String): Result<Unit> = runCatching {
        dataStore
            .value
            .edit { prefs ->
                prefs
                    .getStoreKeys<Any>(dataStoreName)
                    .forEach(prefs::remove)
            }
    }

    override suspend fun <T> removeValue(key: Result<DataStoreKey>, value: T): Result<Unit> = runCatching {
        val k = key.getOrThrow()
        if (value != null) throw IllegalArgumentException("value can not be non-null")
        dataStore.value.edit { it[k.storeKey] = value }
    }

    override suspend fun resetDefaultDataStore(): Result<Unit> = resetDataStore(
        dataStoreName = DataStoreName.DefaultDataStore.name,
    )

    private fun <T> Preferences.get(key: String, type: KType): T? {
        return this[stringPreferencesKey(key)]
            ?.let { serializer.fromString<T>(type, it).getOrThrow() }
            ?: throw KeyedValueDataStoreException.NotFoundException(key)
    }

    private suspend fun <T> getResult(
        key: String,
        getter: Preferences.() -> T,
    ) = getResultFlow(key, getter)
        .first()

    private fun <T> getResultFlow(
        key: String,
        getter: Preferences.() -> T,
    ) = dataStore
        .value
        .data
        .map { preferences ->
            getter(preferences)
                ?: throw KeyedValueDataStoreException.NotFoundException(key)
        }
        .catch { throw it }

    @Suppress("UNCHECKED_CAST")
    private fun <T : Any> Preferences.get(key: String, clazz: KClass<T>): T? {
        return when (clazz) {
            Boolean::class -> this[booleanPreferencesKey(key)] as T
            Int::class -> this[intPreferencesKey(key)] as T
            Long::class -> this[longPreferencesKey(key)] as T
            Float::class -> this[floatPreferencesKey(key)] as T
            Double::class -> this[doublePreferencesKey(key)] as T
            String::class -> this[stringPreferencesKey(key)] as T
            ByteArray::class -> this[byteArrayPreferencesKey(key)] as T
            else -> null
        }
    }

    private operator fun <T> MutablePreferences.set(key: String, type: KType, value: T) {
        if (value == null) {
            this -= stringPreferencesKey(key)
        } else {
            this[stringPreferencesKey(key)] = serializer.toString(type, value).getOrThrow()
        }
    }

    private operator fun <T> MutablePreferences.set(key: String, value: T) {
        if (value == null) {
            this -= stringPreferencesKey(key)
        } else {
            when (value) {
                is Boolean -> this[booleanPreferencesKey(key)] = value
                is Int -> this[intPreferencesKey(key)] = value
                is Long -> this[longPreferencesKey(key)] = value
                is Float -> this[floatPreferencesKey(key)] = value
                is Double -> this[doublePreferencesKey(key)] = value
                is String -> this[stringPreferencesKey(key)] = value
                is ByteArray -> this[byteArrayPreferencesKey(key)] = value
                else -> throw IllegalArgumentException("Cannot set value for key `$key = $value`")
            }
        }
    }

    private fun <T : Any> Preferences.getStoreKeys(storeKey: String): List<Preferences.Key<T>> {
        return asMap()
            .keys
            .asSequence()
            .filter { key ->
                DataStoreKey.get(key.name)
                    .fold(
                        onSuccess = { it.storeKey == storeKey },
                        onFailure = { false },
                    )
            }
            .map {
                @Suppress("UNCHECKED_CAST")
                it as? Preferences.Key<T>
            }
            .filterNotNullTo(mutableListOf())
    }
}
