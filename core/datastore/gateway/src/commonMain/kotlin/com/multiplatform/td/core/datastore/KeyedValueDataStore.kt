package com.multiplatform.td.core.datastore

import kotlinx.coroutines.flow.Flow
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.typeOf

interface KeyedValueDataStore {

    suspend fun <T> setValue(key: Result<DataStoreKey>, value: T): Result<Unit>
    suspend fun <T : Any> getValue(key: Result<DataStoreKey>, valueClass: KClass<T>): Result<T>
    fun <T : Any> getValueFlow(key: Result<DataStoreKey>, valueClass: KClass<T>) : Flow<Result<T>>
    suspend fun <T> setSerializable(key: Result<DataStoreKey>, valueType: KType, value: T): Result<Unit>
    suspend fun <T : Any> getSerializable(key: Result<DataStoreKey>, valueType: KType): Result<T>
    fun <T : Any> getSerializableFlow(key: Result<DataStoreKey>, valueType: KType): Flow<Result<T>>
    suspend fun <T> removeValue(key: Result<DataStoreKey>, value: T): Result<Unit>
    suspend fun resetDataStore(dataStoreName: String): Result<Unit>
    suspend fun resetDefaultDataStore(): Result<Unit>
}

suspend inline fun <reified T : Any> KeyedValueDataStore.getValue(key: Result<DataStoreKey>): Result<T> =
    getValue(key, T::class)

inline fun <reified T : Any> KeyedValueDataStore.getValueFlow(key: Result<DataStoreKey>): Flow<Result<T>> =
    getValueFlow(key, T::class)

suspend inline fun <reified T : Any> KeyedValueDataStore.setSerializable(key: Result<DataStoreKey>, value: T): Result<Unit> =
    setSerializable(key, typeOf<T>(), value)

suspend inline fun <reified T : Any> KeyedValueDataStore.getSerializable(key: Result<DataStoreKey>): Result<T> =
    getSerializable(key, typeOf<T>())

inline fun <reified T : Any> KeyedValueDataStore.getSerializableFlow(key: Result<DataStoreKey>): Flow<Result<T>> =
    getSerializableFlow(key, typeOf<T>())

sealed class KeyedValueDataStoreException : IllegalArgumentException() {

    data class NotFoundException(val key: String) : KeyedValueDataStoreException()
}
