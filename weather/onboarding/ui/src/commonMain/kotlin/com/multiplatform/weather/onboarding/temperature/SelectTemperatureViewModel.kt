package com.multiplatform.weather.onboarding.temperature

import androidx.lifecycle.viewModelScope
import com.multiplatform.td.core.injection.binding.ContributesViewModel
import com.multiplatform.td.core.injection.scopes.FeatureScope
import com.multiplatform.td.core.mvi.MviViewModel
import com.multiplatform.td.core.navigation.FeatureRouter
import com.multiplatform.weather.core.measure.Temperature
import com.multiplatform.weather.onboarding.OnboardingRoute
import com.multiplatform.weather.onboarding.UiState
import com.multiplatform.weather.settings.Settings
import com.multiplatform.weather.settings.usecase.GetSettingsUseCase
import com.multiplatform.weather.settings.usecase.SaveSettingsUseCase
import kotlinx.coroutines.launch

@ContributesViewModel(scope = FeatureScope::class)
internal class SelectTemperatureViewModel(
    private val saveSettingsUseCase: SaveSettingsUseCase,
    private val getSettingsUseCase: GetSettingsUseCase,
    private val featureRouter: FeatureRouter,
) : MviViewModel<SelectTemperatureEvent, SelectTemperatureState>(
    initialState = SelectTemperatureState(),
) {

    init {
        on<SelectTemperatureEvent.OnScreenViewed> {  }
        onClick<SelectTemperatureEvent.OnTryAgainClicked> {
            state = state.copy(uiState = UiState.Loading)
            collectInitialState()
        }
        onClick<SelectTemperatureEvent.OnBackClicked> {
            featureRouter.back()
        }
        onClick<SelectTemperatureEvent.OnContinueClicked> {
            featureRouter.navigate(OnboardingRoute.SelectWindSpeed)
        }
        onClick<SelectTemperatureEvent.OnChanged> {
            saveSettings(temperature = it.temperature)
        }
        viewModelScope.launch { collectInitialState() }
    }

    private suspend fun collectInitialState() = runCatching {
        val saved = getSettingsUseCase()
        saved.fold(
            onSuccess =  {
                updateState(temperature = it.temperatureUnit)
            },
            onFailure = {
                saveSettingsUseCase(Settings.Defaults)
                    .getOrThrow()

                updateState(temperature = Settings.Defaults.temperatureUnit)
            }
        )
    }.onFailure {
        state = state.copy(
            uiState = UiState.Failure.Text(it.message ?: "unknown error")
        )
    }

    private suspend fun saveSettings(temperature: Temperature) = runCatching {
        val saved = getSettingsUseCase().getOrThrow()
        val newSettings = saved.copy(temperatureUnit = temperature)
        saveSettingsUseCase(newSettings).getOrThrow()
        state = state.copy(temperature = temperature)
    }

    private fun updateState(
        isEnabled: Boolean = true,
        temperature: Temperature,
    ) {
        val uiState = UiState.Success(isEnabled)
        state = state.copy(uiState = uiState, temperature = temperature)
    }
}