package com.multiplatform.todo.tasks.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
internal data class TaskDto(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val categoryId: Long,
    val title: String,
    val description: String,
    val dueDateTime: String,
    val duration: Long,
    val status: String,
    val taskId: Long? = null,
)
