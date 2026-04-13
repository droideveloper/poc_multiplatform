package com.multiplatform.td.core.navigation

import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import dev.mokkery.verify
import kotlinx.serialization.Serializable
import kotlin.test.Test

internal class FeatureRouterImplTest {

    private val route = TestFeatureRoute.Route()
    private val routeWithOption = TestFeatureRoute.RouteWithOptions()

    private val navController = mock<NavControllerProxy> {
        every {
            navigate(
                route = route.route,
                navOptions = null,
                navigatorExtras = null,
            )
        } returns Unit
        every {
            navigate(
                route = routeWithOption.route,
                navOptions = routeWithOption.navOptions.toOptions(),
                navigatorExtras = null,
            )
        } returns Unit
        every {
            popBackStack()
        } returns true
    }

    private val featureRouter = FeatureRouterImpl(
        navController = navController,
    )

    @Test
    fun `given route without options will call to nav controller`() {
        featureRouter.navigate(route)

        verify {
            navController.navigate(
                route = route.route,
                navOptions = null,
                navigatorExtras = null,
            )
        }
    }

    @Test
    fun `given route with options will call to nav controller`() {
        featureRouter.navigate(routeWithOption)

        verify {
            navController.navigate(
                route = routeWithOption.route,
                navOptions = routeWithOption.navOptions.toOptions(),
                navigatorExtras = null,
            )
        }
    }

    @Test
    fun `given back will call pop to nav controller`() {
        featureRouter.back()

        verify {
            navController.popBackStack()
        }
    }

    @Test
    fun `given restart will call to nav controller`() {
        featureRouter.restart(route)

        verify {
            navController.navigate(
                route = route.route,
                navOptions = null,
                navigatorExtras = null,
            )
        }
    }
}

internal sealed interface TestRoute {

    @Serializable
    data object Route : TestRoute
}

internal sealed class TestFeatureRoute : FeatureRoute<TestRoute>() {

    class Route : TestFeatureRoute() {

        override val route: TestRoute = TestRoute.Route
    }

    class RouteWithOptions : TestFeatureRoute() {

        override val route: TestRoute = TestRoute.Route

        override val navOptions: FeatureNavOptions = FeatureNavOptions.Builder()
            .singleTop(true)
            .inclusive(true)
            .build()
    }
}
