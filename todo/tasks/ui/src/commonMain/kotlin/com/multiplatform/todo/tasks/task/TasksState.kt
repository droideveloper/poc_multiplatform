package com.multiplatform.todo.tasks.task

import com.multiplatform.todo.tasks.Task
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import org.jetbrains.compose.resources.StringResource

internal data class TasksState(
    val uiState: UiState = UiState.Loading,
    val tasks: List<Task> = emptyList(),
    val date: LocalDate = LocalDate(2025, 3, 4),
    val time: LocalTime = LocalTime(14, 0),
)

internal interface UiState {

    data object Loading : UiState
    data object Success : UiState

    sealed interface Failure : UiState {

        data class Text(val message: String) : Failure
        data class Res(val stringResource: StringResource) : Failure
    }
}
