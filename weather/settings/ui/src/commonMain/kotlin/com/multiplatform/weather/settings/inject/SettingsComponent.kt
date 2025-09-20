package com.multiplatform.weather.settings.inject

import com.multiplatform.td.core.datastore.inject.DataStoreComponent
import com.multiplatform.td.core.injection.scopes.FeatureScope
import com.multiplatform.td.core.navigation.inject.NavigationComponent
import com.multiplatform.weather.settings.GeneratedViewModelModule
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.KmpComponentCreate

@FeatureScope
@Component
abstract class SettingsComponent(
    @Component val dataStoreComponent: DataStoreComponent,
    @Component val navigationComponent: NavigationComponent,
) : GeneratedViewModelModule(), SettingsDataModule {
    companion object;
}

@Suppress("KotlinNoActualForExpect")
@KmpComponentCreate
expect fun createSettingsComponent(
    dataStoreComponent: DataStoreComponent,
    navigationComponent: NavigationComponent,
): SettingsComponent
