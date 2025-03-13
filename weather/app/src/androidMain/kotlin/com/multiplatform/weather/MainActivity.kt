package com.multiplatform.weather

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.multiplatform.td.core.app.ComponentProvider

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val componentProvider = application.componentProvider
        setContent { WeatherApp(component = componentProvider.component) }
    }
}

internal val Application.componentProvider: ComponentProvider
    get() = (this as WeatherApplication)