package com.multiplatform.weather.city

import org.jetbrains.compose.resources.StringResource

internal data class CityState(
    val uiState: UiState = UiState.Loading,
)

sealed interface UiState {

    data object Loading : UiState
    data class Success(
        val cities: List<City>,
        val selectedCities: List<City>,
    ) : UiState

    sealed interface Failure : UiState {

        data class Res(val stringResource: StringResource) : Failure
        data class Text(val message: String) : Failure
    }
}
