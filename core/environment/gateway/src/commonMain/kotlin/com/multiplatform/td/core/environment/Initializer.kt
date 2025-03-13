package com.multiplatform.td.core.environment

interface Initializer<T> {

    operator fun invoke(args: Array<String>)
}
