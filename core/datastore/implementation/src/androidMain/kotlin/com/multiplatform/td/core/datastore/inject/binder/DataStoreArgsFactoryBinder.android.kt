package com.multiplatform.td.core.datastore.inject.binder

import android.content.Context
import com.multiplatform.td.core.datastore.DataStoreArgsFactory
import com.multiplatform.td.core.datastore.DataStoreArgsFactoryImpl
import com.multiplatform.td.core.datastore.DataStoreName
import com.multiplatform.td.core.injection.Binder
import kotlinx.coroutines.CoroutineScope
import me.tatarka.inject.annotations.Inject

@Inject
actual class DataStoreArgsFactoryBinder(
    private val context: Context,
    private val coroutineScope: CoroutineScope,
    private val dataStoreName: DataStoreName = DataStoreName.DefaultDataStore,
) : Binder<DataStoreArgsFactory> {

    actual override fun invoke(): DataStoreArgsFactory =
        DataStoreArgsFactoryImpl(
            context = context,
            coroutineScope = coroutineScope,
            dataStoreName = dataStoreName,
        )
}
