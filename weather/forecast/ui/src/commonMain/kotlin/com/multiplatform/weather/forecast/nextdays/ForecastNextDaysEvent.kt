package com.multiplatform.weather.forecast.nextdays

internal sealed interface ForecastNextDaysEvent {

    data object OnScreenViewed : ForecastNextDaysEvent
    data object OnTryAgainClicked : ForecastNextDaysEvent
    data object OnBackClicked : ForecastNextDaysEvent
    data object OnTick : ForecastNextDaysEvent
}
