package com.multiplatform.weather.onboarding.temperature

import com.multiplatform.weather.core.measure.Temperature
import com.multiplatform.weather.onboarding.UiState

internal data class SelectTemperatureState(
    val uiState: UiState = UiState.Loading,
    val temperature: Temperature = Temperature.Celsius,
)
