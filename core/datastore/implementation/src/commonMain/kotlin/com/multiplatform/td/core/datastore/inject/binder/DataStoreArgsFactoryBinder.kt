package com.multiplatform.td.core.datastore.inject.binder

import com.multiplatform.td.core.datastore.DataStoreArgsFactory
import com.multiplatform.td.core.injection.Binder

expect class DataStoreArgsFactoryBinder : Binder<DataStoreArgsFactory> {
    override fun invoke(): DataStoreArgsFactory
}