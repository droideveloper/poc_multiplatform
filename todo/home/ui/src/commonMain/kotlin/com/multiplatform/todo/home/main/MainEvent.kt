package com.multiplatform.todo.home.main

import com.multiplatform.todo.tasks.Category
import com.multiplatform.todo.tasks.Task

internal sealed interface MainEvent {

    data object OnScreenViewed : MainEvent
    data object OnFilterClicked : MainEvent

    data class OnCategoryClicked(
        val category: Category,
    ) : MainEvent

    data class OnTaskClicked(
        val task: Task,
    ) : MainEvent
}