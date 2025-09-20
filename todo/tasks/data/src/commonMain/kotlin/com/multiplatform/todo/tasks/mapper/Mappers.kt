package com.multiplatform.todo.tasks.mapper

import com.multiplatform.todo.tasks.Category
import com.multiplatform.todo.tasks.CategoryColor
import com.multiplatform.todo.tasks.CategoryDrawableRes
import com.multiplatform.todo.tasks.CategoryTasks
import com.multiplatform.todo.tasks.Task
import com.multiplatform.todo.tasks.TaskStatus
import com.multiplatform.todo.tasks.TaskSubTasks
import com.multiplatform.todo.tasks.db.CategoryDto
import com.multiplatform.todo.tasks.db.CategoryWithTasksDto
import com.multiplatform.todo.tasks.db.TaskDto
import com.multiplatform.todo.tasks.db.TaskWithSubTasksDto
import kotlinx.datetime.LocalDateTime
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.DurationUnit

internal fun CategoryDto.toDomain(): Category = Category(
    id = id,
    name = name,
    description = description,
    color = color.toCategoryColor(),
    iconRes = iconRes?.toCategoryDrawableRes(),
)

internal fun Category.toData(): CategoryDto = CategoryDto(
    id = id,
    name = name,
    description = description,
    color = color.value,
    iconRes = iconRes?.value,
)

internal fun TaskDto.toDomain(
    category: Category,
    parent: Task? = null,
): Task = Task(
    id = id,
    category = category,
    title = title,
    description = description,
    dueDateTime = dueDateTime.toLocalDataTime(),
    duration = duration.toDuration(),
    status = status.toTaskStatus(),
    parent = parent,
)

internal fun Task.toData(): TaskDto = TaskDto(
    id = id,
    categoryId = category.id,
    title = title,
    description = description,
    dueDateTime = dueDateTime.toData(),
    duration = duration.toData(),
    status = status.toData(),
)

internal fun TaskWithSubTasksDto.toDomain(
    category: Category,
): TaskSubTasks {
    val task = task.toDomain(category = category)
    return TaskSubTasks(
        task = task,
        tasks = tasks.map {
            it.toDomain(
                category = category,
                parent = task,
            )
        },
    )
}

internal fun CategoryWithTasksDto.toDomain(): CategoryTasks {
    val category = category.toDomain()
    return CategoryTasks(
        category = category,
        tasks = tasks.map { it.toDomain(category = category) },
    )
}

internal fun LocalDateTime.toData(): String =
    LocalDateTime.Formats.ISO.format(this)

internal fun String.toLocalDataTime(): LocalDateTime =
    LocalDateTime.Formats.ISO.parse(this)

internal fun Duration.toData(): Long = toLong(DurationUnit.MILLISECONDS)

internal fun Long.toCategoryColor(): CategoryColor = CategoryColor.getOrThrow(this)

internal fun String.toCategoryDrawableRes(): CategoryDrawableRes = CategoryDrawableRes.getOrThrow(this)

internal fun Long.toDuration(): Duration =
    this.milliseconds

internal fun TaskStatus.toData(): String = when (this) {
    TaskStatus.Done -> "done"
    TaskStatus.Open -> "open"
    TaskStatus.OverDue -> "over_due"
    TaskStatus.Progress -> "progress"
}

internal fun String.toTaskStatus(): TaskStatus = when (this) {
    "done" -> TaskStatus.Done
    "open" -> TaskStatus.Open
    "over_due" -> TaskStatus.OverDue
    "progress" -> TaskStatus.Progress
    else -> throw IllegalArgumentException("invalid status for task as $this")
}
