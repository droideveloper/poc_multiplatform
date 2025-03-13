package com.multiplatform.weather.settings

import com.multiplatform.weather.city.City
import com.multiplatform.weather.core.measure.Speed
import com.multiplatform.weather.core.measure.Temperature

sealed interface SettingsEvent {

    data object OnScreenViewed : SettingsEvent
    data object OnBackClicked : SettingsEvent
    data object OnTryAgainClicked : SettingsEvent

    sealed interface OnTemperature : SettingsEvent {
        data class Change(val temperature: Temperature) : OnTemperature
    }

    sealed interface OnSpeed : SettingsEvent {
        data class Change(val speed: Speed) : OnSpeed
    }

    sealed interface OnDays : SettingsEvent {
        data object Increment : OnDays
        data object Decrement : OnDays
    }

    sealed interface Operation : SettingsEvent {
        data class Add(
            val city: City,
        ) : Operation
        data class Remove(
            val city: City,
        ) : Operation
    }
}