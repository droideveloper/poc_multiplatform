package com.multiplatform.td.core.repository

interface DataSource<in I, out O> {
    suspend fun get(argument: I): Result<O>
}
