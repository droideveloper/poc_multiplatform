package com.multiplatform.todo.tasks

import kotlinx.datetime.LocalDateTime
import kotlin.time.Duration

data class Task(
    val id: Long = 0,
    val category: Category,
    val title: String,
    val description: String,
    val dueDateTime: LocalDateTime,
    val duration: Duration,
    val status: TaskStatus,
    val parent: Task? = null,
)
