package com.multiplatform.td.core.kotlin

abstract class ValueObjectFactory<in P, out V, out T: IllegalArgumentException> {
    protected abstract val initializer: (P) -> V
    open fun getThrowable(input: P): T? = null
    abstract fun isValid(input: P): Boolean

    fun get(input: P): Result<V> = when {
        isValid(input) -> Result.success(initializer(input))
        else -> Result.failure(getThrowable(input) ?: IllegalArgumentException())
    }

    fun getOrNull(input: P): V? = get(input).getOrNull()
    fun getOrThrow(input: P): V = get(input).getOrThrow()
}
