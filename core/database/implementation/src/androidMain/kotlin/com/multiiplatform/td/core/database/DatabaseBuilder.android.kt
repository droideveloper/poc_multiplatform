package com.multiiplatform.td.core.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

actual data class DatabaseBuilderArgs(
    val context: Context,
    val name: String,
)

actual inline fun <reified T : RoomDatabase> createDatabaseBuilder(
    args: DatabaseBuilderArgs
): RoomDatabase.Builder<T> = Room.databaseBuilder(args.context, args.name)