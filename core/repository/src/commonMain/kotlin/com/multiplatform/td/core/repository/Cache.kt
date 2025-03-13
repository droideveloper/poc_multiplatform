package com.multiplatform.td.core.repository

import kotlin.time.Duration

interface Cache<T> {

    suspend fun get(maxAge: Duration): Result<T>
    suspend fun put(value: T)
    suspend fun clear()
}
