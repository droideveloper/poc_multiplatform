package com.multiplatform.td.core.datastore

interface DataStoreArgsFactory {

    operator fun invoke(): DataStoreArgs
}
