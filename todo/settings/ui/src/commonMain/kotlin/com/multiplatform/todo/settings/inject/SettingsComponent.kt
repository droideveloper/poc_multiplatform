package com.multiplatform.todo.settings.inject

import com.multiplatform.td.core.datastore.inject.DataStoreComponent
import com.multiplatform.td.core.environment.AppVersion
import com.multiplatform.td.core.injection.scopes.FeatureScope
import com.multiplatform.todo.settings.GeneratedViewModelModule
import com.multiplatform.todo.settings.repo.GeneratedBinderModule
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.KmpComponentCreate

@FeatureScope
@Component
abstract class SettingsComponent(
    @Component val dataStoreComponent: DataStoreComponent,
) : GeneratedViewModelModule(), GeneratedBinderModule {
    companion object;

    abstract val version: AppVersion
}

@Suppress("KotlinNoActualForExpect")
@KmpComponentCreate
expect fun createSettingsComponent(
    dataStoreComponent: DataStoreComponent,
): SettingsComponent
