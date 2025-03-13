package com.multiplatform.td.core.repository

interface DataTransformer<in I, out O> {
    fun transform(input: I): O
}
