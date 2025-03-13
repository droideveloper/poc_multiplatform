package com.multiplatform.todo.tasks.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
internal data class CategoryDto(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val description: String? = null,
    val color: Long,
    val iconRes: String? = null,
)
