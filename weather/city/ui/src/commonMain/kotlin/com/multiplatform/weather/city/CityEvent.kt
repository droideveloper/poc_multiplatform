package com.multiplatform.weather.city

internal sealed interface CityEvent {
    data object OnScreenViewed : CityEvent
    data object OnTryAgainClicked : CityEvent

    sealed interface Operation : CityEvent {
        data class Add(val city: City) : Operation
        data class Remove(val city: City) : Operation
    }
}
