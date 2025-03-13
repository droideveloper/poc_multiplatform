package com.multiplatform.td.core.repository

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

internal class TransformingRepository<in I, in M, out O>(
    private val cacheFactory: (I) -> Cache<M>,
    private val dataSource: DataSource<I, M>,
    private val dataTransformer: DataTransformer<M, O>,
) : Repository<I, O> {

    private val mutex = Mutex()
    private val caches = mutableMapOf<I, Cache<M>>()

    override suspend fun query(argument: I, criteria: Criteria): Result<O> =
        query(getOrCreateCache(argument), argument, criteria)

    override suspend fun clear() = mutex.withLock {
        caches.values.forEach { it.clear() }
    }

    private suspend fun query(cache: Cache<M>, argument: I, criteria: Criteria): Result<O> =
        when (criteria) {
            is Criteria.Timed ->
                cache
                    .get(maxAge = criteria.maxAge)
                    .fold(
                        onSuccess = {
                            val data = dataTransformer.transform(it)
                            Result.success(data)
                        },
                        onFailure = {
                            dataSource
                                .get(argument)
                                .onSuccess { cache.put(it) }
                                .mapCatching(dataTransformer::transform)
                        }
                    )
        }

    private suspend fun getOrCreateCache(argument: I): Cache<M> =
        mutex.withLock {
            caches[argument] ?: cacheFactory(argument).also {
                caches[argument] = it
            }
        }
}

fun <I, O> createRepository(
    cacheFactory: (I) -> Cache<O>,
    dataSource: DataSource<I, O>
): Repository<I, O> = TransformingRepository(
    cacheFactory = cacheFactory,
    dataSource = dataSource,
    dataTransformer = object: DataTransformer<O, O> {
        override fun transform(input: O): O = input
    },
)

fun <I, M, O> createRepository(
    cacheFactory: (I) -> Cache<M>,
    dataSource: DataSource<I, M>,
    dataTransformer: DataTransformer<M, O>,
): Repository<I, O> = TransformingRepository(
    cacheFactory = cacheFactory,
    dataSource = dataSource,
    dataTransformer = dataTransformer,
)
