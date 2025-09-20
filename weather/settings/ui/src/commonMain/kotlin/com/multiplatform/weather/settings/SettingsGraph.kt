package com.multiplatform.weather.settings

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.multiplatform.td.core.ui.extensions.animatedComposable

fun NavGraphBuilder.settingsGraph() {
    navigation<Setting.Graph>(startDestination = Setting.Settings) {
        animatedComposable<Setting.Settings> {
            SettingsScreen()
        }
    }
}
