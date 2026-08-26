package com.multiplatform.weather.framework

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.v2.AndroidComposeTestRule

fun <A : ComponentActivity> createWeatherComposeTestRule(
    rule: WeatherActivityScenarioRule<A>,
) = AndroidComposeTestRule(
    activityRule = rule,
    activityProvider = ::getActivityFromScenarioRule,
)

internal fun <A : ComponentActivity> getActivityFromScenarioRule(
    rule: WeatherActivityScenarioRule<A>,
): A {
    var activity: A? = null
    rule.scenario?.onActivity {
        activity = it
    }
    return requireNotNull(activity, { "could not fund activity on scenario" })
}
