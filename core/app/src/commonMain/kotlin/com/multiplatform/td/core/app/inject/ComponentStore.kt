package com.multiplatform.td.core.app.inject

import kotlin.reflect.KClass

interface ComponentStore {

    fun <T : Any> storeIfNotExists(clazz: KClass<T>, factory: () -> T) : T
    fun <T : Any> removeIfExists(clazz: KClass<T>): T?
}

inline fun <reified T : Any> ComponentStore.store(noinline factory: () -> T): T =
    storeIfNotExists(T::class, factory = factory)

inline fun <reified T : Any> ComponentStore.remove(): T? =
    removeIfExists(T::class)
