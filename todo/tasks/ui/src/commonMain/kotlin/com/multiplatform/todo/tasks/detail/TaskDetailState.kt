package com.multiplatform.todo.tasks.detail

import com.multiplatform.todo.tasks.Task
import org.jetbrains.compose.resources.StringResource

internal data class TaskDetailState(
    val uiState: UiState = UiState.Loading,
)

internal interface UiState {

    data object Loading : UiState
    data class Success(
        val task: Task,
    ) : UiState

    sealed interface Failure : UiState {

        data class Text(val message: String) : Failure
        data class Res(val stringResource: StringResource) : Failure
    }
}
