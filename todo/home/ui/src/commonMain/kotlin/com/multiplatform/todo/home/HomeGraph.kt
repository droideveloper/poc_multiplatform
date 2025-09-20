package com.multiplatform.todo.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.multiplatform.todo.home.home.HomeScreen
import com.multiplatform.todo.tasks.Route
import com.multiplatform.todo.tasks.detail.TaskDetailScreen

fun NavGraphBuilder.homeGraph() {
    navigation<Route.Graph>(startDestination = Route.Main) {
        composable<Route.Main> { HomeScreen() }
        composable<Route.Detail> { entry ->
            val route = entry.toRoute<Route.Detail>()
            TaskDetailScreen(route.taskId)
        }
    }
}
