package com.multiplatform.weather.settings

import com.multiplatform.td.core.navigation.FeatureRoute
import kotlinx.serialization.Serializable

sealed interface Setting {

    @Serializable
    data object Graph : Setting

    @Serializable
    data object Settings : Setting
}

sealed class SettingRoute : FeatureRoute<Setting>() {

    data object RestartFeature : SettingRoute() {
        override val route: Setting = Setting.Graph
    }

    data object Settings : SettingRoute() {
        override val route: Setting = Setting.Settings
    }
}