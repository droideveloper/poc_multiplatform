package com.multiplatform.td.core.datastore.inject

import com.multiplatform.td.core.app.AppComponent
import com.multiplatform.td.core.datastore.DataStoreName
import com.multiplatform.td.core.datastore.Datastore
import com.multiplatform.td.core.datastore.inject.binder.DatastoreBinder
import com.multiplatform.td.core.injection.scopes.DataStoreScope
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@DataStoreScope
@Component
abstract class DataStoreComponent(
    @Component val appComponent: AppComponent,
    @get:DataStoreScope @get:Provides val dataStoreName: DataStoreName = DataStoreName.DefaultDataStore,
) : DataStoreModule {
    companion object;

    // TODO this looks like bind-ed multiple times
    @DataStoreScope
    @Provides
    fun bindDatastore(binder: DatastoreBinder): Datastore = binder()
}

fun AppComponent.createDataStoreComponent(
    dataStoreName: DataStoreName = DataStoreName.DefaultDataStore,
): DataStoreComponent = DataStoreComponent.create(
    appComponent = this,
    dataStoreName = dataStoreName,
)
