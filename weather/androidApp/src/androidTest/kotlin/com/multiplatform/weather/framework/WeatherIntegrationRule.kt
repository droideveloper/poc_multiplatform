package com.multiplatform.weather.framework

import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.test.platform.app.InstrumentationRegistry
import com.multiplatform.weather.core.test.UserState
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class WeatherIntegrationRule<A : ComponentActivity>(
    private val weatherActivityScenarioRule: WeatherActivityScenarioRule<A>,
) : TestWatcher() {

    override fun starting(description: Description?) {
        setAnimations(enabled = false)
    }

    override fun finished(description: Description?) {
        weatherActivityScenarioRule.dispose()
        setAnimations(enabled = true)
    }

    fun launchForState(state: UserState) {
        state.setup()
        weatherActivityScenarioRule.launch()
    }

    private fun setAnimations(enabled: Boolean) {
        val value = if (enabled) 1 else 0
        with(InstrumentationRegistry.getInstrumentation().uiAutomation) {
            executeShellCommand("settings put global ${Settings.Global.WINDOW_ANIMATION_SCALE} $value")
            executeShellCommand("settings put global ${Settings.Global.TRANSITION_ANIMATION_SCALE} $value")
            executeShellCommand("settings put global ${Settings.Global.ANIMATOR_DURATION_SCALE} $value")
        }
    }
}

fun <A : ComponentActivity> createWeatherIntegrationRule(
    rule: WeatherActivityScenarioRule<A>,
) = WeatherIntegrationRule(rule)
