package com.multiplatform.weather.onboarding.city

import androidx.lifecycle.viewModelScope
import com.multiplatform.td.core.injection.binding.ContributesViewModel
import com.multiplatform.td.core.injection.scopes.FeatureScope
import com.multiplatform.td.core.mvi.MviViewModel
import com.multiplatform.td.core.navigation.FeatureRouter
import com.multiplatform.weather.city.City
import com.multiplatform.weather.city.usecase.GetSelectedCitiesUseCase
import com.multiplatform.weather.onboarding.OnboardingRoute
import com.multiplatform.weather.onboarding.UiState
import kotlinx.coroutines.launch

@ContributesViewModel(scope = FeatureScope::class)
internal class SelectCityViewModel(
    private val getSelectedCitiesUseCase: GetSelectedCitiesUseCase,
    private val featureRouter: FeatureRouter,
) : MviViewModel<SelectCityEvent, SelectCityState>(
    initialState = SelectCityState(),
) {

    private var selectedCities = emptyList<City>()

    init {
        on<SelectCityEvent.OnScreenViewed> { }
        onClick<SelectCityEvent.OnTryAgainClicked> {
            state = state.copy(uiState = UiState.Loading)
            collectInitialState()
        }
        onClick<SelectCityEvent.Operation.Add> {
            selectedCities += it.city
            updateUiState(isEnabled = selectedCities.isNotEmpty())
        }
        onClick<SelectCityEvent.Operation.Remove> {
            selectedCities -= it.city
            updateUiState(isEnabled = selectedCities.isNotEmpty())
        }
        onClick<SelectCityEvent.OnContinueClicked> {
            featureRouter.navigate(OnboardingRoute.SelectTemperature)
        }
        viewModelScope.launch { collectInitialState() }
    }

    private suspend fun collectInitialState() = runCatching {
        val result = getSelectedCitiesUseCase()
        result.fold(
            onSuccess = {
                selectedCities += it
                updateUiState(it.isNotEmpty())
            },
            onFailure = {
                updateUiState(isEnabled = false)
            },
        )
    }

    private fun updateUiState(isEnabled: Boolean = true) {
        val uiState = state.uiState
        state = if (uiState is UiState.Success) {
            state.copy(uiState = uiState.copy(isContinueOrDoneEnabled = isEnabled))
        } else {
            state.copy(uiState = UiState.Success(isContinueOrDoneEnabled = isEnabled))
        }
    }
}
