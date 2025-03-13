@file:OptIn(ExperimentalTime::class)

package com.multiplatform.weather.forecast.today

import androidx.lifecycle.viewModelScope
import com.multiplatform.td.core.injection.binding.ContributesViewModel
import com.multiplatform.td.core.injection.scopes.FeatureScope
import com.multiplatform.td.core.mvi.MviViewModel
import com.multiplatform.td.core.navigation.FeatureRouter
import com.multiplatform.weather.city.City
import com.multiplatform.weather.city.usecase.GetSelectedCitiesUseCase
import com.multiplatform.weather.forecast.ForecastRoute
import com.multiplatform.weather.forecast.usecase.GetForecastUseCase
import com.multiplatform.weather.settings.SettingRoute
import com.multiplatform.weather.settings.usecase.GetSettingsUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@ContributesViewModel(scope = FeatureScope::class)
internal class ForecastViewModel(
    private val getSelectedCitiesUseCase: GetSelectedCitiesUseCase,
    private val getForecastUseCase: GetForecastUseCase,
    private val getSettingsUseCase: GetSettingsUseCase,
    private val featureRouter: FeatureRouter,
): MviViewModel<ForecastEvent, ForecastState>(
    initialState = ForecastState(),
) {

    init {
        on<ForecastEvent.OnScreenViewed> {  }
        onClick<ForecastEvent.OnCityChanged> {
            state = state.copy(uiState = UiState.Loading)
            collectInitialState(city = it.city)
        }
        onClick<ForecastEvent.OnSettingsClicked> {
            featureRouter.navigate(SettingRoute.Settings)
        }
        onClick<ForecastEvent.OnNextDaysClicked> {
            featureRouter.navigate(ForecastRoute.NextDays(it.city.id))
        }
        on<ForecastEvent.OnTick> {
            state = state.copy(currentLocalDateTime = currentLocalDateTime())
        }
        viewModelScope.launch { collectInitialState() }
        collectSettings()
        collectSelectedCities()
    }

    private suspend fun collectInitialState(city: City? = null) = runCatching {
        val selectedCities = getSelectedCitiesUseCase().getOrThrow()
        val selectedCity = city ?: selectedCities.first()
        val forecast = getForecastUseCase(selectedCity).getOrThrow()
        state = state.copy(
            currentLocalDateTime = currentLocalDateTime(),
            uiState = UiState.Success,
            forecast = forecast,
            selectedCities = selectedCities,
            city = selectedCity,
        )
    }.onFailure {
        state = state.copy(
            uiState = UiState.Failure.Text(it.message ?: "unknown error")
        )
    }

    private fun collectSettings() {
        getSettingsUseCase.asFlow()
            .onEach {
                it.fold(
                    onSuccess = { settings ->
                        state = state.copy(
                            settings = settings,
                        )
                    },
                    onFailure = { error ->
                        error.printStackTrace()
                    }
                )
            }
            .launchIn(viewModelScope)
    }

    private fun collectSelectedCities() {
        getSelectedCitiesUseCase.asFlow()
            .onEach {
                it.fold(
                    onSuccess = { selectedCities ->
                        val hasCitySelectedInSavedCities = selectedCities.any { city -> city == state.city }
                        val selectedCity = when {
                            hasCitySelectedInSavedCities -> state.city
                            else -> selectedCities.first()
                        }
                        if (selectedCity != state.city) {
                            val isStateCityDefault = state.city == City.Defaults
                            if (isStateCityDefault.not()) {
                                collectInitialState(city = selectedCities.first())
                            }
                        } else {
                            state = state.copy(
                                selectedCities = selectedCities,
                            )
                        }
                    },
                    onFailure = { error ->
                        error.printStackTrace()
                    }
                )
            }
            .launchIn(viewModelScope)
    }

    private fun currentLocalDateTime() = Clock.System.now().toLocalDateTime(
        timeZone = TimeZone.currentSystemDefault(),
    )
}