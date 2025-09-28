package com.multiplatform.weather.settings

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.multiplatform.td.core.ui.KoverIgnore
import com.multiplatform.td.core.ui.extensions.animatedComposable

@KoverIgnore
fun NavGraphBuilder.settingsGraph() {
    navigation<Setting.Graph>(startDestination = Setting.Settings) {
        animatedComposable<Setting.Settings> {
            SettingsScreen()
        }
    }
}
