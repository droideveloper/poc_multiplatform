package com.multiplatform.weather.onboarding

import org.jetbrains.compose.resources.StringResource

sealed interface UiState {

    data object Loading : UiState
    data class Success(
        val isContinueOrDoneEnabled: Boolean = false,
    ) : UiState
    sealed interface Failure : UiState {
        data class Res(val stringResource: StringResource) : Failure
        data class Text(val message: String) : Failure
    }
}