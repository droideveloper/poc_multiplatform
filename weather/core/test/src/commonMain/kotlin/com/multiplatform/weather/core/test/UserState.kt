package com.multiplatform.weather.core.test

interface UserState {

    companion object {
        const val OnboardingDone = "OnboardingDone"
        const val OnboardingInComplete = "OnboardingInComplete"
    }

    fun setup()
}
