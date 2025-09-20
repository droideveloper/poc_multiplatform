package com.multiplatform.weather.onboarding.speed

import com.multiplatform.weather.core.measure.Speed
import com.multiplatform.weather.onboarding.temperature.SelectTemperatureEvent

internal interface SelectWindSpeedEvent {

    data object OnScreenViewed : SelectWindSpeedEvent
    data object OnBackClicked : SelectWindSpeedEvent
    data object OnTryAgainClicked : SelectWindSpeedEvent
    data object OnDoneClicked : SelectWindSpeedEvent
    data class OnChanged(
        val speed: Speed,
    ) : SelectWindSpeedEvent
}
