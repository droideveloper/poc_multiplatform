package com.multiplatform.weather.forecast.today

import com.multiplatform.weather.city.City

internal sealed interface ForecastEvent {

    data object OnScreenViewed : ForecastEvent
    data object OnSettingsClicked : ForecastEvent
    data class OnNextDaysClicked(
        val city: City,
    ) : ForecastEvent
    data object OnTick : ForecastEvent
    data class OnCityChanged(
        val city: City,
    ) : ForecastEvent
}
