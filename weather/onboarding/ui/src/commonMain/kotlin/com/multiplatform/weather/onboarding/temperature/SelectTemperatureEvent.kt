package com.multiplatform.weather.onboarding.temperature

import com.multiplatform.weather.core.measure.Temperature

internal sealed interface SelectTemperatureEvent {

    data object OnScreenViewed : SelectTemperatureEvent
    data object OnBackClicked : SelectTemperatureEvent
    data object OnTryAgainClicked : SelectTemperatureEvent
    data object OnContinueClicked : SelectTemperatureEvent
    data class OnChanged(
        val temperature: Temperature,
    ) : SelectTemperatureEvent
}