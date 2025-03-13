package com.multiplatform.weather.onboarding.splash

internal sealed interface SplashEvent {

    data object OnScreenViewed : SplashEvent
}