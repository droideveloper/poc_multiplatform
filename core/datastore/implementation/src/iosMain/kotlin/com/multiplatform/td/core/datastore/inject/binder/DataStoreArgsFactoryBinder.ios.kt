package com.multiplatform.td.core.datastore.inject.binder

import com.multiplatform.td.core.datastore.DataStoreArgsFactory
import com.multiplatform.td.core.datastore.DataStoreArgsFactoryImpl
import com.multiplatform.td.core.datastore.DataStoreName
import com.multiplatform.td.core.injection.Binder
import kotlinx.coroutines.CoroutineScope
import me.tatarka.inject.annotations.Inject
import platform.Foundation.NSFileManager

@Inject
actual class DataStoreArgsFactoryBinder(
    private val fileManager: NSFileManager,
    private val coroutineScope: CoroutineScope,
    private val dataStoreName: DataStoreName = DataStoreName.DefaultDataStore,
) : Binder<DataStoreArgsFactory> {

    actual override fun invoke(): DataStoreArgsFactory =
        DataStoreArgsFactoryImpl(
            fileManager = fileManager,
            coroutineScope = coroutineScope,
            dataStoreName = dataStoreName,
        )
}