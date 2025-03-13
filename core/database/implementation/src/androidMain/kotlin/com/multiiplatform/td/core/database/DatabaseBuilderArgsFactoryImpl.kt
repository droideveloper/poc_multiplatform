package com.multiiplatform.td.core.database

import android.content.Context

internal class DatabaseBuilderArgsFactoryImpl(
    private val context: Context,
    private val databaseName: DatabaseName = DatabaseName.DefaultDatabase,
) : DatabaseBuilderArgsFactory {

    override fun invoke(): DatabaseBuilderArgs =
        context.ofDatabaseBuilderArgs(databaseName)
}

internal fun Context.ofDatabaseBuilderArgs(
    databaseName: DatabaseName,
): DatabaseBuilderArgs = DatabaseBuilderArgs(
    context = this,
    name = databaseName.asFileName,
)
