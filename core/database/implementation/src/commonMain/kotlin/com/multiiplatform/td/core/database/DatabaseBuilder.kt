package com.multiiplatform.td.core.database

import androidx.room.RoomDatabase

expect class DatabaseBuilderArgs

expect inline fun <reified T : RoomDatabase> createDatabaseBuilder(
    args: DatabaseBuilderArgs
): RoomDatabase.Builder<T>
