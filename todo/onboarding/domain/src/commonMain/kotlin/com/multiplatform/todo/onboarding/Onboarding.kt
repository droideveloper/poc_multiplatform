package com.multiplatform.todo.onboarding

data class Onboarding(
    val areCategoriesSetUp: Boolean,
    val areNotificationsSetUp: Boolean,
    val isCompleted: Boolean,
) {

    companion object {
        val Default = Onboarding(
            areCategoriesSetUp = false,
            areNotificationsSetUp = false,
            isCompleted = false,
        )
    }
}
