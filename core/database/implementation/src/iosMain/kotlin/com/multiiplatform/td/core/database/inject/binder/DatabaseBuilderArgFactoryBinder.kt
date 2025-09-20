package com.multiiplatform.td.core.database.inject.binder

import com.multiiplatform.td.core.database.DatabaseBuilderArgsFactory
import com.multiiplatform.td.core.database.DatabaseBuilderArgsFactoryImpl
import com.multiiplatform.td.core.database.DatabaseName
import com.multiplatform.td.core.injection.Binder
import me.tatarka.inject.annotations.Inject
import platform.Foundation.NSFileManager

@Inject
actual class DatabaseBuilderArgFactoryBinder(
    private val fileManager: NSFileManager,
    private val databaseName: DatabaseName = DatabaseName.DefaultDatabase,
) : Binder<DatabaseBuilderArgsFactory> {

    actual override fun invoke(): DatabaseBuilderArgsFactory =
        DatabaseBuilderArgsFactoryImpl(fileManager, databaseName)
}
