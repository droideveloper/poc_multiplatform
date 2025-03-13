package com.multiplatform.td.core.repository

import kotlin.time.Duration
import kotlin.time.TimeSource

object CacheFactory {

    fun <T> inMemory(): Cache<T> = InMemoryCache(
        timeSource = TimeSource.Monotonic,
    )

    fun <I, T> inMemoryFactory(): (I) -> Cache<T> = { inMemory() }

    fun <T> empty(): Cache<T> = object: Cache<T> {
        override suspend fun get(maxAge: Duration): Result<T> =
            Result.failure(NotImplementedError())
        override suspend fun put(value: T) = Unit
        override suspend fun clear() = Unit
    }

    fun <I, T> emptyFactory(): (I) -> Cache<T> = { empty() }
}
