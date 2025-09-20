package com.multiiplatform.td.core.database

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.multiplatform.td.core.coroutines.DispatcherProvider

internal class RoomDatabaseConfigImpl(
    private val dispatcherProvider: DispatcherProvider,
) : RoomDatabaseConfig {

    override fun invoke(config: RoomDatabase.Builder<*>) {
        with(config) {
            fallbackToDestructiveMigrationOnDowngrade(dropAllTables = true)
            setDriver(BundledSQLiteDriver())
            setQueryCoroutineContext(dispatcherProvider.io)
        }
    }
}
