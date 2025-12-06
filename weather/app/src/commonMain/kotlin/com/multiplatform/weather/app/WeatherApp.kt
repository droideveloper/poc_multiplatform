package com.multiplatform.weather.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.multiiplatform.td.core.database.DatabaseName
import com.multiiplatform.td.core.database.composable.DatabaseContext
import com.multiplatform.td.core.app.AppComponent
import com.multiplatform.td.core.app.composable.AppContext
import com.multiplatform.td.core.datastore.composable.DataStoreContext
import com.multiplatform.td.core.navigation.composable.NavigationContext
import com.multiplatform.td.core.ui.KoverIgnore
import com.multiplatform.weather.core.ui.FwTheme
import com.multiplatform.weather.forecast.forecastGraph
import com.multiplatform.weather.onboarding.Onboarding
import com.multiplatform.weather.onboarding.onboardingGraph
import com.multiplatform.weather.settings.settingsGraph

@KoverIgnore
@Composable
fun WeatherApp(
    component: AppComponent,
) {
    val navController = rememberNavController()
    val component = remember { component }

    AppContext(component, navController) {
        DatabaseContext(
            databaseName = DatabaseName.of("city_database"),
        ) {
            DataStoreContext {
                NavigationContext {
                    FwTheme {
                        NavHost(navController, startDestination = Onboarding.Graph) {
                            onboardingGraph()
                            settingsGraph()
                            forecastGraph()
                        }
                    }
                }
            }
        }
    }
}
