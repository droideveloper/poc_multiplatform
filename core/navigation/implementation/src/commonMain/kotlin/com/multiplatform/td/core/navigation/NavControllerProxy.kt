package com.multiplatform.td.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator

interface NavControllerProxy {

    fun <T: Any> navigate(
        route: T,
        navOptions: NavOptions? = null,
        navigatorExtras: Navigator.Extras? = null,
    )

    fun popBackStack(): Boolean
}

fun createNavController(
    navController: NavController
): NavControllerProxy = object : NavControllerProxy {

    override fun <T : Any> navigate(
        route: T,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ) = navController.navigate(route, navOptions, navigatorExtras)

    override fun popBackStack(): Boolean =
        navController.popBackStack()
}
