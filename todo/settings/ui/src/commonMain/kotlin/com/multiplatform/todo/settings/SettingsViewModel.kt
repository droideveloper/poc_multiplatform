package com.multiplatform.todo.settings

import androidx.lifecycle.viewModelScope
import com.multiplatform.td.core.environment.AppVersion
import com.multiplatform.td.core.injection.binding.ContributesViewModel
import com.multiplatform.td.core.injection.scopes.FeatureScope
import com.multiplatform.td.core.mvi.MviViewModel
import com.multiplatform.todo.settings.usecase.GetSettingsUseCase
import com.multiplatform.todo.settings.usecase.SaveSettingsUseCase
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.minutes

@ContributesViewModel(FeatureScope::class)
internal class SettingsViewModel(
    private val getSettingsUseCase: GetSettingsUseCase,
    private val saveSettingsUseCase: SaveSettingsUseCase,
    version: AppVersion,
) : MviViewModel<SettingsEvent, SettingsState>(
    initialState = SettingsState(version = version),
) {

    init {
        on<SettingsEvent.OnScreenViewed> {
            // TODO log or send event to analytics
        }
        onClick<SettingsEvent.OnChange.Notification> {
            saveSettings(
                settings = state.settings.copy(
                    isNotificationEnabled = it.enabled,
                ),
            )
        }
        onClick<SettingsEvent.OnChange.Duration.Increment> {
            saveSettings(
                settings = state.settings.copy(
                    notifyBefore = state.settings.notifyBefore.plus(1.minutes),
                ),
            )
        }
        onClick<SettingsEvent.OnChange.Duration.Decrement> {
            saveSettings(
                settings = state.settings.copy(
                    notifyBefore = state.settings.notifyBefore.minus(1.minutes),
                ),
            )
        }
        viewModelScope.launch { collectInitialState() }
    }

    private suspend fun collectInitialState() = runCatching {
        val settings = getSettingsUseCase().getOrNull() ?: Settings.Default
        state = state.copy(
            uiState = UiState.Success,
            settings = settings,
        )
    }.onFailure { it.printStackTrace() }

    private suspend fun saveSettings(settings: Settings) = runCatching {
        saveSettingsUseCase(settings).getOrThrow()
        state = state.copy(settings = settings)
    }.onFailure { it.printStackTrace() }
}
