package com.multiplatform.weather.onboarding.city

import com.multiplatform.weather.city.City

internal sealed interface SelectCityEvent {

    data object OnScreenViewed : SelectCityEvent
    data object OnTryAgainClicked : SelectCityEvent
    data object OnContinueClicked : SelectCityEvent

    sealed interface Operation : SelectCityEvent {
        data class Add(
            val city: City,
        ) : Operation
        data class Remove(
            val city: City,
        ) : Operation
    }
}
