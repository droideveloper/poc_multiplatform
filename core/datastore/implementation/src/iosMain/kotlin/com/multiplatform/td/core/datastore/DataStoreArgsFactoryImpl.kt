package com.multiplatform.td.core.datastore

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.CoroutineScope
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

internal class DataStoreArgsFactoryImpl(
    private val fileManager: NSFileManager,
    private val coroutineScope: CoroutineScope,
    private val dataStoreName: DataStoreName = DataStoreName.DefaultDataStore,
) : DataStoreArgsFactory {

    override fun invoke(): DataStoreArgs =
        fileManager.ofDataStoreArgs(
            dataStoreName = dataStoreName,
            scope = coroutineScope,
        )
}

@OptIn(ExperimentalForeignApi::class)
internal fun NSFileManager.ofDataStoreArgs(
    dataStoreName: DataStoreName,
    scope: CoroutineScope,
): DataStoreArgs = DataStoreArgs(
    name = lazy {
        requireNotNull(
            URLForDirectory(
                directory = NSDocumentDirectory,
                inDomain = NSUserDomainMask,
                appropriateForURL = null,
                create = false,
                error = null,
            ),
        ).path + "/${dataStoreName.asFileName}"
    },
    scope = scope,
)
