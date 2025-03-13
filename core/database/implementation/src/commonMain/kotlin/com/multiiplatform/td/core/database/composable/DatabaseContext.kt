package com.multiiplatform.td.core.database.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import com.multiiplatform.td.core.database.DatabaseName
import com.multiiplatform.td.core.database.inject.DatabaseComponent
import com.multiiplatform.td.core.database.inject.createDatabaseComponent
import com.multiplatform.td.core.app.composable.LocalAppComponent
import com.multiplatform.td.core.app.composable.LocalComponentStore
import com.multiplatform.td.core.app.error.CompositionContextException
import com.multiplatform.td.core.app.inject.store

val LocalDatabaseComponent: ProvidableCompositionLocal<DatabaseComponent> = compositionLocalOf {
    throw CompositionContextException.NotFound(DatabaseComponent::class)
}

@Composable
fun DatabaseContext(
    databaseName: DatabaseName = DatabaseName.DefaultDatabase,
    content: @Composable () -> Unit,
) {
    val appComponent = LocalAppComponent.current
    val componentStore = LocalComponentStore.current

    val databaseComponent: DatabaseComponent = componentStore.store {
        appComponent.createDatabaseComponent(
            databaseName = databaseName,
        )
    }

    CompositionLocalProvider(
        LocalDatabaseComponent provides databaseComponent
    ) {
        content()
    }
}
