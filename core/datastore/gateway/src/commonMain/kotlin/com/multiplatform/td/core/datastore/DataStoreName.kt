package com.multiplatform.td.core.datastore

class DataStoreName internal constructor(
    val name: String
) {

    val asFileName: String
        get() = "$name.preferences_pb"

    companion object {
        val DefaultDataStore: DataStoreName = DataStoreName(DataStoreKey.DefaultStore)
        fun of(name: String): DataStoreName = DataStoreName(name)
    }
}
