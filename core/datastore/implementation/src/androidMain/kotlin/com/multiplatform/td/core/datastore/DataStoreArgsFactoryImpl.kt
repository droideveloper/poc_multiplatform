package com.multiplatform.td.core.datastore

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import java.io.File

internal class DataStoreArgsFactoryImpl(
    private val context: Context,
    private val coroutineScope: CoroutineScope,
    private val dataStoreName: DataStoreName = DataStoreName.DefaultDataStore,
) : DataStoreArgsFactory {

    override fun invoke(): DataStoreArgs =
        context.ofDataStoreArgs(
            dataStoreName = dataStoreName,
            scope = coroutineScope,
        )
}

internal fun Context.ofDataStoreArgs(
    dataStoreName: DataStoreName,
    scope: CoroutineScope,
): DataStoreArgs = DataStoreArgs(
    name = lazy { File(filesDir, dataStoreName.asFileName).absolutePath },
    scope = scope,
)
