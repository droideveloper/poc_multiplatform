package com.multiplatform.weather.onboarding.speed

import androidx.lifecycle.viewModelScope
import com.multiplatform.td.core.app.inject.ComponentStore
import com.multiplatform.td.core.app.inject.remove
import com.multiplatform.td.core.injection.binding.ContributesViewModel
import com.multiplatform.td.core.injection.scopes.FeatureScope
import com.multiplatform.td.core.mvi.MviViewModel
import com.multiplatform.td.core.navigation.FeatureNavOptions
import com.multiplatform.td.core.navigation.FeatureRouter
import com.multiplatform.weather.core.measure.Speed
import com.multiplatform.weather.forecast.ForecastRoute
import com.multiplatform.weather.onboarding.Onboarding
import com.multiplatform.weather.onboarding.UiState
import com.multiplatform.weather.onboarding.inject.OnboardingComponent
import com.multiplatform.weather.onboarding.usecase.SaveOnboardingUseCase
import com.multiplatform.weather.settings.Settings
import com.multiplatform.weather.settings.usecase.GetSettingsUseCase
import com.multiplatform.weather.settings.usecase.SaveSettingsUseCase
import kotlinx.coroutines.launch

@ContributesViewModel(scope = FeatureScope::class)
internal class SelectWindSpeedViewModel(
    private val getSettingsUseCase: GetSettingsUseCase,
    private val saveSettingsUseCase: SaveSettingsUseCase,
    private val saveOnboardingUseCase: SaveOnboardingUseCase,
    private val featureRouter: FeatureRouter,
    private val componentStore: ComponentStore,
) : MviViewModel<SelectWindSpeedEvent, SelectWindSpeedState>(
    initialState = SelectWindSpeedState(),
) {

    init {
        on<SelectWindSpeedEvent.OnScreenViewed> { }
        onClick<SelectWindSpeedEvent.OnTryAgainClicked> {
            state = state.copy(uiState = UiState.Loading)
            collectInitialState()
        }
        onClick<SelectWindSpeedEvent.OnBackClicked> {
            featureRouter.back()
        }
        onClick<SelectWindSpeedEvent.OnDoneClicked> {
            onCompleteOnboarding()
        }
        onClick<SelectWindSpeedEvent.OnChanged> {
            saveSettings(speed = it.speed)
        }
        viewModelScope.launch { collectInitialState() }
    }

    private suspend fun collectInitialState() = runCatching {
        val saved = getSettingsUseCase()
        saved.fold(
            onSuccess = {
                updateState(speed = it.speedUnit)
            },
            onFailure = {
                saveSettingsUseCase(Settings.Defaults)
                    .getOrThrow()

                updateState(speed = Settings.Defaults.speedUnit)
            },
        )
    }

    private suspend fun saveSettings(speed: Speed) = runCatching {
        val saved = getSettingsUseCase().getOrThrow()
        val newSettings = saved.copy(speedUnit = speed)
        saveSettingsUseCase(newSettings).getOrThrow()
        state = state.copy(speed = speed)
    }

    private suspend fun onCompleteOnboarding() = runCatching {
        saveOnboardingUseCase(isCompleted = true).getOrThrow()
        featureRouter.navigate(
            ForecastRoute.Forecast,
            options = FeatureNavOptions.Builder().apply {
                inclusive(true)
                popUpTo(Onboarding.Graph)
            }.build(),
        ).also {
            // if there is onboarding component it will be cleared in here
            componentStore.remove<OnboardingComponent>()
        }
    }

    private fun updateState(
        isEnabled: Boolean = true,
        speed: Speed,
    ) {
        val uiState = UiState.Success(isEnabled)
        state = state.copy(uiState = uiState, speed = speed)
    }
}
