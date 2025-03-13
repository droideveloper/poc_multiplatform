package com.multiplatform.todo.tasks.db

import androidx.room.Embedded
import androidx.room.Relation

internal data class TaskWithSubTasksDto(
    @Embedded val task: TaskDto,
    @Relation(
        entity = TaskDto::class,
        parentColumn = "id",
        entityColumn = "taskId",
    )
    val tasks: List<TaskDto>
)