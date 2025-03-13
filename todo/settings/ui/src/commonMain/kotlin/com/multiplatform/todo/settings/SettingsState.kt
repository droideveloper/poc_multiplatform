package com.multiplatform.todo.settings

import com.multiplatform.td.core.environment.AppVersion
import org.jetbrains.compose.resources.StringResource

internal data class SettingsState(
    val uiState: UiState = UiState.Loading,
    val settings: Settings = Settings.Default,
    val version: AppVersion,
)

internal interface UiState {
    data object Loading : UiState
    data object Success : UiState

    sealed interface Failure : UiState {

        data class Text(val message: String) : Failure
        data class Res(val stringResource: StringResource) : Failure
    }
}
