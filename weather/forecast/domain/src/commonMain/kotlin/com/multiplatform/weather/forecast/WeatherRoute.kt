package com.multiplatform.weather.forecast

import com.multiplatform.td.core.navigation.FeatureRoute
import kotlinx.serialization.Serializable

sealed interface WeatherRoute {

    @Serializable
    data object Graph : WeatherRoute

    @Serializable
    data object Forecast : WeatherRoute

    @Serializable
    data class NextDays(
        val selectedCityId: Long,
    ) : WeatherRoute
}

sealed class ForecastRoute : FeatureRoute<WeatherRoute>() {

    data object ResetFeature : ForecastRoute() {
        override val route: WeatherRoute = WeatherRoute.Graph
    }

    data object Forecast : ForecastRoute() {
        override val route: WeatherRoute = WeatherRoute.Forecast
    }

    data class NextDays(
        val selectedCityId: Long,
    ) : ForecastRoute() {
        override val route: WeatherRoute = WeatherRoute.NextDays(selectedCityId)
    }
}