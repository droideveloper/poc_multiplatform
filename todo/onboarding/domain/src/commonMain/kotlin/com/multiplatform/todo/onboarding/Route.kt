package com.multiplatform.todo.onboarding

import com.multiplatform.td.core.navigation.FeatureRoute
import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object Graph : Route

    @Serializable
    data object Splash : Route

    @Serializable
    data object Intro : Route

    @Serializable
    data object Categories : Route
}

sealed class OnboardingRoute : FeatureRoute<Route>() {

    data object GraphRoute : OnboardingRoute() {
        override val route: Route = Route.Graph
    }

    data object SplashRoute : OnboardingRoute() {
        override val route: Route = Route.Splash
    }

    data object IntroRoute : OnboardingRoute() {
        override val route: Route = Route.Intro
    }

    data object CategoriesRoute : OnboardingRoute() {
        override val route: Route = Route.Categories
    }
}
