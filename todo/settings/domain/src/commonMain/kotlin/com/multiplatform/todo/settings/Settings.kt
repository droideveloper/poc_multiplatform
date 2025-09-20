package com.multiplatform.todo.settings

import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

data class Settings(
    val isNotificationEnabled: Boolean,
    val notifyBefore: Duration,
) {

    companion object {
        val Default = Settings(
            isNotificationEnabled = true,
            notifyBefore = 30.minutes,
        )
    }
}
