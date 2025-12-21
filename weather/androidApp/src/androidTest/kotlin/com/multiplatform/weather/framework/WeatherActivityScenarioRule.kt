package com.multiplatform.weather.framework

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.test.core.app.ActivityScenario
import org.junit.rules.ExternalResource

class WeatherActivityScenarioRule<A : ComponentActivity>(
    private val intent: Intent,
) : ExternalResource() {

    var scenario: ActivityScenario<A>? = null

    fun launch(): ActivityScenario<A> =
        ActivityScenario.launch<A>(intent).also {
            scenario = it
        }

    fun dispose() {
        scenario?.close()
        scenario = null
    }

    override fun after() {
        dispose()
    }
}

fun <A : ComponentActivity> createWeatherActivityScenarioRule(
    intent: Intent,
) = WeatherActivityScenarioRule<A>(intent = intent)
