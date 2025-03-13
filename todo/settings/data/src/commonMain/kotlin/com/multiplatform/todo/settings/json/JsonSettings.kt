package com.multiplatform.todo.settings.json

import kotlinx.serialization.Serializable

@Serializable
internal data class JsonSettings(
    val isNotificationEnabled: Boolean,
    val notifyBefore: Long,
)