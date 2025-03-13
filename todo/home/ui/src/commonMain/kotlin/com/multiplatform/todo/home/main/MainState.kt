package com.multiplatform.todo.home.main

import com.multiplatform.todo.tasks.Task
import com.multiplatform.todo.tasks.TaskSubTasks
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import org.jetbrains.compose.resources.StringResource

internal data class MainState(
    val uiState: UiState = UiState.Loading,
    val todayTasks: List<TaskSubTasks> = emptyList(),
    val todayScheduleTasks: List<Task> = emptyList(),
    val time: LocalTime = LocalTime(12, 0),
    val date: LocalDate = LocalDate(2025, 3, 4),
)


internal interface UiState {
    data object Loading : UiState
    data object Success : UiState

    sealed interface Failure : UiState {

        data class Text(val message: String) : Failure
        data class Res(val stringResource: StringResource) : Failure
    }
}
