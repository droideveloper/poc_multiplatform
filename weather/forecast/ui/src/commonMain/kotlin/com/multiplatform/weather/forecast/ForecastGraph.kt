package com.multiplatform.weather.forecast

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.multiplatform.td.core.ui.extensions.animatedComposable
import com.multiplatform.weather.forecast.nextdays.ForecastNextDaysScreen
import com.multiplatform.weather.forecast.today.ForecastScreen

fun NavGraphBuilder.forecastGraph() {
    navigation<WeatherRoute.Graph>(
        startDestination = WeatherRoute.Forecast,
    ) {
        animatedComposable<WeatherRoute.Forecast> {
            ForecastScreen()
        }
        animatedComposable<WeatherRoute.NextDays> { stack ->
            val route = stack.toRoute<WeatherRoute.NextDays>()
            ForecastNextDaysScreen(route.selectedCityId)
        }
    }
}