package com.multiplatform.todo.tasks

import com.multiplatform.td.core.navigation.FeatureRoute
import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object Graph : Route

    @Serializable
    data object Main : Route

    @Serializable
    data class Detail(val taskId: Long) : Route
}

sealed class AppRoute : FeatureRoute<Route>() {

    data object Graph : AppRoute() {
        override val route: Route = Route.Graph
    }

    data object Main : AppRoute() {
        override val route: Route = Route.Main
    }

    data class Detail(val taskId: Long) : AppRoute() {
        override val route: Route = Route.Detail(taskId)
    }
}