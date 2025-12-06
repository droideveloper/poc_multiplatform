package com.multiplatform.weather

import androidx.test.core.app.ApplicationProvider
import com.multiplatform.td.core.app.inject.ComponentStore
import com.multiplatform.td.core.app.inject.store
import com.multiplatform.td.core.datastore.DataStoreName
import com.multiplatform.td.core.datastore.inject.DataStoreComponent
import com.multiplatform.td.core.datastore.inject.createDataStoreComponent
import com.multiplatform.weather.core.test.TestComponent
import com.multiplatform.weather.core.test.TestComponentProvider
import com.multiplatform.weather.core.test.createTestComponent

class TestWeatherApplication : WeatherApplication(), TestComponentProvider {

    val componentStore: ComponentStore =
        component.componentStore

    val dataStoreComponent: DataStoreComponent =
        componentStore.store {
            component.createDataStoreComponent(
                dataStoreName = DataStoreName.DefaultDataStore,
            )
        }

    override val testComponent: TestComponent =
        createTestComponent(
            appComponent = component,
            dataStoreComponent = dataStoreComponent,
        )

    override fun onCreate() {
        super.onCreate()
    }
}

val testComponent: TestComponent =
    ApplicationProvider.getApplicationContext<TestWeatherApplication>()
        .testComponent
