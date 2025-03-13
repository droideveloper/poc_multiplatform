package com.multiplatform.todo.onboarding.splash

internal sealed interface SplashEvent {

    data object OnScreenViewed : SplashEvent
}
