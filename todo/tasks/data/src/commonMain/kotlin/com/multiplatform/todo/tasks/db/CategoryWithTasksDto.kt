package com.multiplatform.todo.tasks.db

import androidx.room.Embedded
import androidx.room.Relation

internal data class CategoryWithTasksDto(
    @Embedded val category: CategoryDto,
    @Relation(
        entity = TaskDto::class,
        parentColumn = "id",
        entityColumn = "categoryId",
    )
    val tasks: List<TaskDto>,
)
