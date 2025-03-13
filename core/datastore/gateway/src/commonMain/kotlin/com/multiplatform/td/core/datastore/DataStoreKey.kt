package com.multiplatform.td.core.datastore

import com.multiplatform.td.core.kotlin.ValueObjectFactory
import kotlin.jvm.JvmInline

@JvmInline
value class DataStoreKey private constructor(val storeKey: String) {

    companion object : ValueObjectFactory<String, DataStoreKey, DataStoreKeyException>() {

        internal const val DefaultStore = "default_store"
        private const val Sign = ":"
        private const val StoreIndex = 0
        private const val KeyIndex = 1
        private const val KeySize = 2

        override val initializer: (String) -> DataStoreKey = ::DataStoreKey

        override fun isValid(input: String): Boolean {
            val values = input.split(Sign)
            if (values.count() == KeySize) {
                return values[StoreIndex].isNotEmpty() && values[KeyIndex].isNotEmpty()
            }
            return true
        }

        override fun getThrowable(input: String): DataStoreKeyException? {
            val values = input.split(Sign)
            return when  {
                values[StoreIndex].isEmpty() -> DataStoreKeyException.InvalidStoreName
                values[KeyIndex].isEmpty() -> DataStoreKeyException.InvalidKeyName
                values.count() > KeySize -> DataStoreKeyException.InvalidInput
                else -> null
            }
        }

        fun defaultStoreKey(key: String): Result<DataStoreKey> = get("$DefaultStore:$key")
    }
}

sealed class DataStoreKeyException: IllegalArgumentException() {

    data object InvalidKeyName : DataStoreKeyException()
    data object InvalidStoreName : DataStoreKeyException()
    data object InvalidInput : DataStoreKeyException()
}
