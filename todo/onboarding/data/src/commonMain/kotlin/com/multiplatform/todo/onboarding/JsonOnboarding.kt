package com.multiplatform.todo.onboarding

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class JsonOnboarding(
    @SerialName("are_categories_setup") val areCategoriesSetUp: Boolean,
    @SerialName("are_notifications_setup") val areNotificationsSetUp: Boolean,
    @SerialName("is_completed") val isCompleted: Boolean,
)
