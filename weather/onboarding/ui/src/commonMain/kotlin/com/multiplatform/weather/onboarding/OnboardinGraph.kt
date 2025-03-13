package com.multiplatform.weather.onboarding

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.multiplatform.td.core.ui.extensions.animatedComposable
import com.multiplatform.weather.onboarding.city.SelectCityScreen
import com.multiplatform.weather.onboarding.speed.SelectWindSpeedScreen
import com.multiplatform.weather.onboarding.splash.SplashScreen
import com.multiplatform.weather.onboarding.temperature.SelectTemperatureScreen

fun NavGraphBuilder.onboardingGraph() {
    navigation<Onboarding.Graph>(startDestination = Onboarding.Splash) {
        composable<Onboarding.Splash> {
            SplashScreen()
        }
        composable<Onboarding.SelectCity> {
            SelectCityScreen()
        }
        animatedComposable<Onboarding.SelectTemperature> {
            SelectTemperatureScreen()
        }
        animatedComposable<Onboarding.SelectWindSpeed> {
            SelectWindSpeedScreen()
        }
    }
}
