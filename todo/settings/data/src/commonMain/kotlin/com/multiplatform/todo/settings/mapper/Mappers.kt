package com.multiplatform.todo.settings.mapper

import com.multiplatform.todo.settings.Settings
import com.multiplatform.todo.settings.json.JsonSettings
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.DurationUnit

internal fun Settings.toData(): JsonSettings =
    JsonSettings(
        isNotificationEnabled = isNotificationEnabled,
        notifyBefore = notifyBefore.toData(),
    )

internal fun JsonSettings.toDomain(): Settings =
    Settings(
        isNotificationEnabled = isNotificationEnabled,
        notifyBefore = notifyBefore.toDomain(),
    )

internal fun Duration.toData(): Long =
    toLong(DurationUnit.MINUTES)

internal fun Long.toDomain(): Duration =
    this.minutes
