package com.multiplatform.weather.forecast.inject

import com.multiplatform.td.core.datastore.inject.DataStoreComponent
import com.multiplatform.td.core.injection.scopes.FeatureScope
import com.multiplatform.td.core.navigation.inject.NavigationComponent
import com.multiplatform.td.core.network.inject.NetworkComponent
import com.multiplatform.weather.forecast.GeneratedViewModelModule
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.KmpComponentCreate

@FeatureScope
@Component
abstract class ForecastComponent(
    @Component val networkComponent: NetworkComponent,
    @Component val navigationComponent: NavigationComponent,
    @Component val dataStoreComponent: DataStoreComponent,
) : GeneratedViewModelModule(), ForecastDataModule {
    companion object;
}

@KmpComponentCreate
expect fun createForecastComponent(
    networkComponent: NetworkComponent,
    navigationComponent: NavigationComponent,
    dataStoreComponent: DataStoreComponent,
) : ForecastComponent
