package com.multiiplatform.td.core.database

import androidx.room.Room
import androidx.room.RoomDatabase

actual data class DatabaseBuilderArgs(
    val name: String,
)

actual inline fun <reified T : RoomDatabase> createDatabaseBuilder(
    args: DatabaseBuilderArgs,
): RoomDatabase.Builder<T> = Room.databaseBuilder(args.name)
