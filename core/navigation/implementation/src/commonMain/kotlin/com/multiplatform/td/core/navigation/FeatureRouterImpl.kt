package com.multiplatform.td.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.navOptions

internal class FeatureRouterImpl(
    private val navController: NavController,
) : FeatureRouter {

    override fun <T : Any> navigate(route: FeatureRoute<T>) = when {
        route.navOptions != null -> {
            val navOptions = requireNotNull(route.navOptions).toOptions()
            navController.navigate(route.route, navOptions)
        }
        else -> navController.navigate(route.route)
    }

    override fun <T : Any> navigate(route: FeatureRoute<T>, options: FeatureNavOptions) {
        navController.navigate(route.route, options.toOptions())
    }

    override fun <T : Any> restart(route: FeatureRoute<T>) =
        navigate(route)

    override fun back() {
        navController.popBackStack()
    }

    private fun FeatureNavOptions.toOptions(): NavOptions = navOptions {
        restoreState = this@toOptions.restoreState
        launchSingleTop = this@toOptions.singleTop
        this@toOptions.popUpTo?.let { route ->
            popUpTo(route = route) {
                inclusive = this@toOptions.inclusive
                saveState = this@toOptions.saveState
            }
        }
    }
}
