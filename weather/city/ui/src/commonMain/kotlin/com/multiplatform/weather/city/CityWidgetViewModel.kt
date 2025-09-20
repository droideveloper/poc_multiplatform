package com.multiplatform.weather.city

import androidx.lifecycle.viewModelScope
import com.multiplatform.td.core.injection.binding.ContributesViewModel
import com.multiplatform.td.core.injection.scopes.FeatureScope
import com.multiplatform.td.core.mvi.MviViewModel
import com.multiplatform.weather.city.usecase.DeleteCityUseCase
import com.multiplatform.weather.city.usecase.GetCitiesUseCase
import com.multiplatform.weather.city.usecase.GetSelectedCitiesUseCase
import com.multiplatform.weather.city.usecase.PopulateDatabaseUseCase
import com.multiplatform.weather.city.usecase.SaveCityUseCase
import kotlinx.coroutines.launch
import tdmultiplatform.weather.city.ui.generated.resources.Res
import tdmultiplatform.weather.city.ui.generated.resources.city_ui_failure_message

@ContributesViewModel(scope = FeatureScope::class)
internal class CityWidgetViewModel(
    private val getCitiesUseCase: GetCitiesUseCase,
    private val getSelectedCitiesUseCase: GetSelectedCitiesUseCase,
    private val populateDatabaseUseCase: PopulateDatabaseUseCase,
    private val saveCityUseCase: SaveCityUseCase,
    private val deleteCityUseCase: DeleteCityUseCase,
) : MviViewModel<CityEvent, CityState>(
    initialState = CityState(),
) {

    init {
        on<CityEvent.OnScreenViewed> { }
        onClick<CityEvent.OnTryAgainClicked> {
            state = state.copy(uiState = UiState.Loading)
            collectInitialState()
        }
        onClick<CityEvent.Operation.Add> {
            addCity(city = it.city)
        }
        onClick<CityEvent.Operation.Remove> {
            removeCity(city = it.city)
        }
        viewModelScope.launch { collectInitialState() }
    }

    private suspend fun collectInitialState() = runCatching {
        val result = getCitiesUseCase()
        result.fold(
            onSuccess = {
                if (it.isEmpty()) {
                    populateThenCollectState()
                } else {
                    collectState(it)
                }
            },
            onFailure = {
                it.printStackTrace()
                state = state.copy(
                    uiState = when (val message = it.message) {
                        null -> UiState.Failure.Res(Res.string.city_ui_failure_message)
                        else -> UiState.Failure.Text(message)
                    },
                )
            },
        )
    }
        .onFailure {
            it.printStackTrace()
            state = state.copy(
                uiState = UiState.Failure.Res(Res.string.city_ui_failure_message),
            )
        }

    private suspend fun populateThenCollectState() {
        populateDatabaseUseCase()
        val result = getCitiesUseCase()
        val items = result.getOrThrow()
        val selectedItems = getSelectedCitiesUseCase().getOrNull() ?: emptyList()
        state = state.copy(
            uiState = UiState.Success(
                cities = items,
                selectedCities = selectedItems,
            ),
        )
    }

    private suspend fun collectState(cities: List<City>) {
        val selectedItems = getSelectedCitiesUseCase().getOrNull() ?: emptyList()
        state = state.copy(
            uiState = UiState.Success(
                cities = cities,
                selectedCities = selectedItems,
            ),
        )
    }

    private suspend fun addCity(city: City) = runCatching {
        val result = saveCityUseCase(city)
        result.fold(
            onSuccess = {
                val uiState = state.uiState
                if (uiState is UiState.Success) {
                    state = state.copy(
                        uiState = uiState.copy(
                            selectedCities = uiState.selectedCities + city,
                        ),
                    )
                }
            },
            onFailure = { it.printStackTrace() },
        )
    }

    private suspend fun removeCity(city: City) = runCatching {
        val result = deleteCityUseCase(city)
        result.fold(
            onSuccess = {
                val uiState = state.uiState
                if (uiState is UiState.Success) {
                    state = state.copy(
                        uiState = uiState.copy(
                            selectedCities = uiState.selectedCities - city,
                        ),
                    )
                }
            },
            onFailure = { it.printStackTrace() },
        )
    }
}
