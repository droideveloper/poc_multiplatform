package com.multiplatform.todo.settings

internal sealed interface SettingsEvent {

    data object OnScreenViewed : SettingsEvent

    sealed interface OnChange : SettingsEvent {

        data class Notification(val enabled: Boolean) : OnChange

        sealed interface Duration : OnChange {

            data object Increment : Duration
            data object Decrement : Duration
        }
    }
}
