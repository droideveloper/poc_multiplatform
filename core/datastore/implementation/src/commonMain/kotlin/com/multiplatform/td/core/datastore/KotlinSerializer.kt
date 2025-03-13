package com.multiplatform.td.core.datastore

import com.multiplatform.td.core.injection.binding.ContributesBinder
import com.multiplatform.td.core.injection.scopes.DataStoreScope
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kotlin.reflect.KType

@ContributesBinder(scope = DataStoreScope::class)
internal class KotlinSerializer : Serializer {

    override fun <T> toString(type: KType, value: T): Result<String> = runCatching {
        Json.encodeToString(Json.serializersModule.serializer(type), value)
    }

    override fun <T> fromString(type: KType, json: String): Result<T> = runCatching {
        @Suppress("UNCHECKED_CAST")
        val result = Json.decodeFromString(
            deserializer = Json.serializersModule.serializer(type),
            string = json,
        ) as? T
        checkNotNull(result) { "`$json` cannot be deserialized to type $type." }
    }
}
