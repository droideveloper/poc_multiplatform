package com.multiplatform.td.core.datastore.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import com.multiplatform.td.core.app.composable.LocalAppComponent
import com.multiplatform.td.core.app.composable.LocalComponentStore
import com.multiplatform.td.core.app.error.CompositionContextException
import com.multiplatform.td.core.app.inject.store
import com.multiplatform.td.core.datastore.DataStoreName
import com.multiplatform.td.core.datastore.inject.DataStoreComponent
import com.multiplatform.td.core.datastore.inject.createDataStoreComponent

val LocalDataSoreComponent: ProvidableCompositionLocal<DataStoreComponent> = compositionLocalOf {
    throw CompositionContextException.NotFound(DataStoreComponent::class)
}

@Composable
fun DataStoreContext(
    dataStoreName: DataStoreName = DataStoreName.DefaultDataStore,
    content: @Composable () -> Unit,
) {
    val appComponent = LocalAppComponent.current
    val componentStore = LocalComponentStore.current

    val dataStoreComponent = componentStore.store {
        appComponent.createDataStoreComponent(
            dataStoreName = dataStoreName,
        )
    }

    CompositionLocalProvider(
        LocalDataSoreComponent provides dataStoreComponent
    ) {
        content()
    }
}
