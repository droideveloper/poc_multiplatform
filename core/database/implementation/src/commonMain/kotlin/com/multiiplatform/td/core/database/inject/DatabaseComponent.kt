package com.multiiplatform.td.core.database.inject

import com.multiiplatform.td.core.database.DatabaseName
import com.multiplatform.td.core.app.AppComponent
import com.multiplatform.td.core.injection.scopes.DatabaseScope
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@DatabaseScope
@Component
abstract class DatabaseComponent(
    @Component val appComponent: AppComponent,
    @get:DatabaseScope @get:Provides val databaseName: DatabaseName = DatabaseName.DefaultDatabase,
) : DatabaseModule {
    companion object;
}

fun AppComponent.createDatabaseComponent(
    databaseName: DatabaseName = DatabaseName.DefaultDatabase
): DatabaseComponent = DatabaseComponent.create(
    appComponent = this,
    databaseName = databaseName,
)
