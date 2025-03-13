package com.multiiplatform.td.core.database

import androidx.room.RoomDatabase

interface RoomDatabaseConfig : (RoomDatabase.Builder<*>) -> Unit