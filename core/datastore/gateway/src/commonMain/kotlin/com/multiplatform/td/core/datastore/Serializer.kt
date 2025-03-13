package com.multiplatform.td.core.datastore

import kotlin.reflect.KType

interface Serializer {

    fun <T> toString(type: KType, value: T): Result<String>
    fun <T> fromString(type: KType, json: String): Result<T>
}
