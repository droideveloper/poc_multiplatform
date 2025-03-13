package com.multiplatform.td.core.repository

import kotlin.time.Duration
import kotlin.time.TimeMark
import kotlin.time.TimeSource

internal class InMemoryCache<T>(
    private val timeSource: TimeSource,
) : Cache<T> {

    private var cache: Pair<T, TimeMark>? = null

    override suspend fun get(maxAge: Duration): Result<T> =
        cache
            ?.let { (value, mark) ->
                value.takeIf { mark.elapsedNow() <= maxAge }
            }
            ?.let { Result.success(it) }
            ?: when {
                cache == null -> Result.failure(CacheException.Empty)
                else -> Result.failure(CacheException.Expired)
            }

    override suspend fun put(value: T) {
        cache = Pair(value, timeSource.markNow())
    }

    override suspend fun clear() {
        cache = null
    }
}
