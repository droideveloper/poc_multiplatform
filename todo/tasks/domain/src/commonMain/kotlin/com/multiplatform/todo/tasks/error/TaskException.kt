package com.multiplatform.todo.tasks.error

sealed class TaskException(
    override val message: String?,
) : IllegalArgumentException(message) {

    data class NotFound(
        val taskId: Long,
    ) : TaskException(
        message = "task with id $taskId does not exists.",
    )

    data class EmptyDataForDate(
        val dateSting: String,
    ) : TaskException(
        message = "tasks with date $dateSting does not exists.",
    )

    data object EmptyData : TaskException(
        message = "tasks are empty, does not exists.",
    )
}