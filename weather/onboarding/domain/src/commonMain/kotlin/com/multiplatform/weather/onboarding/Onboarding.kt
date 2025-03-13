package com.multiplatform.weather.onboarding

import com.multiplatform.td.core.navigation.FeatureRoute
import kotlinx.serialization.Serializable

sealed interface Onboarding {

    @Serializable
    data object Graph : Onboarding

    @Serializable
    data object Splash : Onboarding

    @Serializable
    data object SelectCity : Onboarding

    @Serializable
    data object SelectTemperature : Onboarding

    @Serializable
    data object SelectWindSpeed : Onboarding
}

sealed class OnboardingRoute : FeatureRoute<Onboarding>() {

    data object RestartFeature : OnboardingRoute() {
        override val route: Onboarding = Onboarding.Graph
    }

    data object Splash : OnboardingRoute() {
        override val route: Onboarding = Onboarding.Splash
    }

    data object SelectCity : OnboardingRoute() {
        override val route: Onboarding = Onboarding.SelectCity
    }

    data object SelectTemperature : OnboardingRoute() {
        override val route: Onboarding = Onboarding.SelectTemperature
    }

    data object SelectWindSpeed : OnboardingRoute() {
        override val route: Onboarding = Onboarding.SelectWindSpeed
    }
}