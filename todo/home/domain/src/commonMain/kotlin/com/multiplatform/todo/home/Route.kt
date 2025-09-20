package com.multiplatform.todo.home

import com.multiplatform.td.core.navigation.FeatureRoute
import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object Graph : Route

    @Serializable
    data object Main : Route

    @Serializable
    data object Task : Route

    @Serializable
    data object Calender : Route

    @Serializable
    data object Settings : Route
}

sealed class HomeRoute : FeatureRoute<Route>() {

    data object Graph : HomeRoute() {
        override val route: Route = Route.Graph
    }

    data object Main : HomeRoute() {
        override val route: Route = Route.Main
    }

    data object Task : HomeRoute() {
        override val route: Route = Route.Task
    }

    data object Calendar : HomeRoute() {
        override val route: Route = Route.Calender
    }

    data object Settings : HomeRoute() {
        override val route: Route = Route.Settings
    }
}
