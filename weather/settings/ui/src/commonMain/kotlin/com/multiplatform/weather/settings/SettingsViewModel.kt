package com.multiplatform.weather.settings

import androidx.lifecycle.viewModelScope
import com.multiplatform.td.core.datastore.KeyedValueDataStoreException
import com.multiplatform.td.core.injection.binding.ContributesViewModel
import com.multiplatform.td.core.injection.scopes.FeatureScope
import com.multiplatform.td.core.mvi.MviViewModel
import com.multiplatform.td.core.navigation.FeatureRouter
import com.multiplatform.weather.city.City
import com.multiplatform.weather.city.usecase.GetSelectedCitiesUseCase
import com.multiplatform.weather.settings.usecase.GetSettingsUseCase
import com.multiplatform.weather.settings.usecase.SaveSettingsUseCase
import kotlinx.coroutines.launch

@ContributesViewModel(scope = FeatureScope::class)
internal class SettingsViewModel(
    private val getSettingsUseCase: GetSettingsUseCase,
    private val saveSettingsUseCase: SaveSettingsUseCase,
    private val getSelectedCitiesUseCase: GetSelectedCitiesUseCase,
    private val featureRouter: FeatureRouter,
) : MviViewModel<SettingsEvent, SettingsState>(
    initialState = SettingsState()
) {

    private var selectedCities = emptyList<City>()

    init {
        on<SettingsEvent.OnScreenViewed> {
            // TODO add initial stuff or logging
        }
        onClick<SettingsEvent.OnBackClicked> {
            // TODO
            //  if there is no city present we should change it
            //  if settings changed by any properties we should let ui know or forecast view so it could react to it
            if (selectedCities.isEmpty()) {
                // show error to user that they need to select a city
            } else {
                featureRouter.back()
            }
        }
        onClick<SettingsEvent.OnTemperature.Change> {
            updateState {
                val newSettings = settings.value
                saveSettingsChange(newSettings.copy(temperatureUnit = it.temperature))
            }
        }
        onClick<SettingsEvent.OnDays.Increment> {
            updateState {
                val newSettings = settings.value
                saveSettingsChange(newSettings.copy(days = newSettings.days + 1))
            }
        }
        onClick<SettingsEvent.OnDays.Decrement> {
            updateState {
                val newSettings = settings.value
                saveSettingsChange(newSettings.copy(days = newSettings.days - 1))
            }
        }
        onClick<SettingsEvent.OnSpeed.Change> {
            updateState {
                val newSettings = settings.value
                saveSettingsChange(newSettings.copy(speedUnit = it.speed))
            }
        }
        onClick<SettingsEvent.OnTryAgainClicked> {
            state = state.copy(uiState = UiState.Loading)
            collectInitialState()
        }
        onClick<SettingsEvent.Operation.Add> {
            selectedCities += it.city
        }
        onClick<SettingsEvent.Operation.Remove> {
            // TODO when it is removed I should remove cache too from settings
            selectedCities -= it.city
        }
        viewModelScope.launch { collectInitialState() }
    }

    private suspend fun collectInitialState() = runCatching {
        selectedCities += getSelectedCitiesUseCase().getOrThrow()
        val result = getSettingsUseCase()
        result.fold(
            onSuccess = {
                val uiState = UiState.Success().also { state -> state.update(it) }
                state = state.copy(uiState = uiState)
            },
            onFailure = {
                if (it is KeyedValueDataStoreException.NotFoundException) {
                    state = state.copy(uiState = UiState.Success())
                } else {
                    it.printStackTrace()
                }
            }
        )
    }

    private suspend fun saveSettingsChange(
        settings: Settings,
    ) {
        val result = saveSettingsUseCase(settings)
        result.fold(
            onSuccess = {
                updateState {
                    update(settings)
                }
            },
            onFailure = {
                it.printStackTrace()
            }
        )
    }

    private suspend fun updateState(
        block : suspend UiState.Success.() -> Unit,
    ) {
        val uiState = state.uiState
        if (uiState is UiState.Success) {
            block(uiState)
        }
    }
}
