package com.multiplatform.td.core.injection

interface Provider<T> {
    operator fun invoke(): T
}