package com.multiplatform.weather.onboarding.speed

import com.multiplatform.weather.core.measure.Speed
import com.multiplatform.weather.onboarding.UiState

internal data class SelectWindSpeedState(
    val uiState: UiState = UiState.Loading,
    val speed: Speed = Speed.KilometersPerHour,
)
