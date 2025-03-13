package com.multiplatform.td.core.datastore.inject.binder

import com.multiplatform.td.core.datastore.Datastore
import com.multiplatform.td.core.datastore.KeyedValueDataStore
import com.multiplatform.td.core.datastore.KeyedValueDataStoreImpl
import com.multiplatform.td.core.datastore.Serializer
import com.multiplatform.td.core.injection.Binder
import kotlinx.coroutines.CoroutineScope
import me.tatarka.inject.annotations.Inject

@Inject
class KeyedValueDataStoreBinder(
    private val datastore: Datastore,
    private val serializer: Serializer,
) : Binder<KeyedValueDataStore> {

    override fun invoke(): KeyedValueDataStore =
        KeyedValueDataStoreImpl(
            dataStore = lazy { datastore.create() },
            serializer = serializer,
        )
}
