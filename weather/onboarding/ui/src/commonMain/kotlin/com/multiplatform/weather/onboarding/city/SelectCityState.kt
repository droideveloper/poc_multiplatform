package com.multiplatform.weather.onboarding.city

import com.multiplatform.weather.onboarding.UiState

internal data class SelectCityState(
    val uiState: UiState = UiState.Loading,
)