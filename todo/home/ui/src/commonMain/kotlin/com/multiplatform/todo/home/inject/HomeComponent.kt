package com.multiplatform.todo.home.inject

import androidx.navigation.NavHostController
import com.multiiplatform.td.core.database.inject.DatabaseComponent
import com.multiplatform.td.core.injection.scopes.FeatureScope
import com.multiplatform.td.core.navigation.FeatureRouter
import com.multiplatform.td.core.navigation.inject.binder.FeatureRouterBinder
import com.multiplatform.todo.home.GeneratedViewModelModule
import com.multiplatform.todo.tasks.inject.TasksDatabaseModule
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.KmpComponentCreate
import me.tatarka.inject.annotations.Provides

@FeatureScope
@Component
abstract class HomeComponent(
    @Component val databaseComponent: DatabaseComponent,
    @get:Provides @get:FeatureScope val navHostController: NavHostController,
) : GeneratedViewModelModule(), TasksDatabaseModule {
    companion object;

    @FeatureScope
    @Provides
    fun bindFeatureRouter(binder: FeatureRouterBinder): FeatureRouter = binder()
}

@KmpComponentCreate
expect fun createHomeComponent(
    databaseComponent: DatabaseComponent,
    navHostController: NavHostController,
) : HomeComponent
