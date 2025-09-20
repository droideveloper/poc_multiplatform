package com.multiplatform.todo.tasks.db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor

@Database(
    entities = [
        CategoryDto::class,
        TaskDto::class,
    ],
    version = 1,
    exportSchema = true,
)
@ConstructedBy(TodoDatabaseConstructor::class)
abstract class TodoDatabase : RoomDatabase() {
    internal abstract fun taskDao(): TaskDao
    internal abstract fun categoryDao(): CategoryDao
}

@Suppress("KotlinNoActualForExpect")
expect object TodoDatabaseConstructor : RoomDatabaseConstructor<TodoDatabase> {
    override fun initialize(): TodoDatabase
}
