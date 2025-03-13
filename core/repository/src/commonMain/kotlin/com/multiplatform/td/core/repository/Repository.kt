package com.multiplatform.td.core.repository

interface Repository<in I, out O> {
    suspend fun query(argument: I, criteria: Criteria): Result<O>
    suspend fun clear()
}

suspend fun <O> Repository<Unit, O>.query(criteria: Criteria) = query(Unit, criteria)
