package com.multiplatform.todo.onboarding.categories

import org.jetbrains.compose.resources.StringResource

internal data class CategoriesState(
    val uiState: UiState = UiState.Loading,
)

internal sealed interface UiState {

    data object Loading : UiState
    data object Success : UiState

    sealed interface Failure : UiState {

        data class Text(val message: String) : Failure
        data class Res(val res: StringResource) : Failure
    }
}
