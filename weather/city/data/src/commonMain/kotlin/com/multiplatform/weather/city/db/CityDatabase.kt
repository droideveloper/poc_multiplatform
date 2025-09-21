package com.multiplatform.weather.city.db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor

@Database(
    entities = [CityDto::class],
    version = 1,
    exportSchema = true,
)
@ConstructedBy(CityDatabaseConstructor::class)
abstract class CityDatabase : RoomDatabase() {
    internal abstract fun cityDao(): CityDao
}

@Suppress("KotlinNoActualForExpect")
expect object CityDatabaseConstructor : RoomDatabaseConstructor<CityDatabase> {
    override fun initialize(): CityDatabase
}
