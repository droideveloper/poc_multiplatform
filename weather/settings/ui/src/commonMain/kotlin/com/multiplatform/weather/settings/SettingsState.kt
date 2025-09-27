package com.multiplatform.weather.settings

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.multiplatform.td.core.environment.AppVersion
import org.jetbrains.compose.resources.StringResource

data class SettingsState(
    val uiState: UiState = UiState.Loading,
)

sealed interface UiState {

    data object Loading : UiState

    data class Success(
        val settings: MutableState<Settings> = mutableStateOf(Settings.Defaults),
        val version: AppVersion = AppVersion("N/A"),
    ) : UiState {

        fun update(settings: Settings) {
            this.settings.value = settings
        }
    }

    sealed interface Failure : UiState {
        data class Res(val stringResource: StringResource) : Failure
        data class Text(val message: String) : Failure
    }
}
