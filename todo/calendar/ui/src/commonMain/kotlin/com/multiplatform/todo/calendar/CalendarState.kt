package com.multiplatform.todo.calendar

import com.multiplatform.todo.tasks.Task
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import org.jetbrains.compose.resources.StringResource

internal data class CalendarState(
    val uiState: UiState = UiState.Loading,
    val week: Week = Week.Default,
    val date: LocalDate = LocalDate(2025, 3, 4),
    val time: LocalTime = LocalTime(19, 0),
    val tasks: List<Task> = emptyList(),
)

internal interface UiState {
    data object Loading : UiState
    data object Success : UiState

    sealed interface Failure : UiState {

        data class Text(val message: String) : Failure
        data class Res(val stringResource: StringResource) : Failure
    }
}
