package com.multiplatform.td.core.injection

// https://github.com/evant/kotlin-inject/issues/469
// Kotlin-Inject could not bind, internal impl into interfaces because of the way it generates code
interface Binder<T> {
    operator fun invoke(): T
}