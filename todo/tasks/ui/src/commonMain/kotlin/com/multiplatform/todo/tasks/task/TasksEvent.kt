package com.multiplatform.todo.tasks.task

import com.multiplatform.todo.tasks.Task

internal interface TasksEvent {

    data object OnScreenViewed : TasksEvent
    data object OnFilterClicked : TasksEvent
    data class OnTaskClicked(
        val task: Task,
    ) : TasksEvent
}
