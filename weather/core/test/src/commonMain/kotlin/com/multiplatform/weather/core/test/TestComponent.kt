package com.multiplatform.weather.core.test

import com.multiplatform.td.core.app.AppComponent
import com.multiplatform.td.core.datastore.KeyedValueDataStore
import com.multiplatform.td.core.datastore.inject.DataStoreComponent
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.KmpComponentCreate

@Component
abstract class TestComponent(
    @Component val appComponent: AppComponent,
    @Component val dataStoreComponent: DataStoreComponent,
) {
    companion object;

    abstract val dataStore: KeyedValueDataStore
}

@Suppress("KotlinNoActualForExpect")
@KmpComponentCreate
expect fun createTestComponent(
    appComponent: AppComponent,
    dataStoreComponent: DataStoreComponent,
): TestComponent
