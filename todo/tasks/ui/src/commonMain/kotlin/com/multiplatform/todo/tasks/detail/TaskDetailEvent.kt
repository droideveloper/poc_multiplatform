package com.multiplatform.todo.tasks.detail

internal interface TaskDetailEvent {

    data object OnScreenViewed : TaskDetailEvent

    data object OnBackClicked : TaskDetailEvent
}