package com.multiiplatform.td.core.database

import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

internal class DatabaseBuilderArgsFactoryImpl(
    private val fileManager: NSFileManager,
    private val databaseName: DatabaseName = DatabaseName.DefaultDatabase,
) : DatabaseBuilderArgsFactory {

    override fun invoke(): DatabaseBuilderArgs =
        fileManager.ofDatabaseBuilderArgs(databaseName)
}

@OptIn(ExperimentalForeignApi::class)
internal fun NSFileManager.ofDatabaseBuilderArgs(
    databaseName: DatabaseName,
) : DatabaseBuilderArgs = DatabaseBuilderArgs(
    name = requireNotNull(
        URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null
        )
    ).path + "/${databaseName.asFileName}"
)