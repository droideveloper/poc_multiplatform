package com.multiplatform.todo.onboarding

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.multiplatform.todo.onboarding.categories.CategoriesScreen
import com.multiplatform.todo.onboarding.intro.IntroScreen
import com.multiplatform.todo.onboarding.splash.SplashScreen

fun NavGraphBuilder.onboardingGraph() {
    navigation<Route.Graph>(startDestination = Route.Splash) {
        composable<Route.Splash> { SplashScreen() }
        composable<Route.Intro> { IntroScreen()  }
        composable<Route.Categories> { CategoriesScreen() }
    }
}
