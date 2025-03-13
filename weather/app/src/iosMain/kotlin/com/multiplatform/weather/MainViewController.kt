package com.multiplatform.weather

import androidx.compose.ui.window.ComposeUIViewController
import com.multiplatform.td.core.app.createAppComponent

fun mainViewController() = ComposeUIViewController {
    WeatherApp(
        component = createAppComponent(),
    )
}