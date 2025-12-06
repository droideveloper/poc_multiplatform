package com.multiplatform.weather.framework

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.test.core.app.ApplicationProvider
import com.multiplatform.weather.core.test.UserState
import org.junit.Rule

interface WeatherTest<A : ComponentActivity> {

    @get:Rule
    val activityScenarioRule: WeatherActivityScenarioRule<A>

    @get:Rule
    val integrationRule: WeatherIntegrationRule<A>

    @get:Rule
    val composeTestRule: AndroidComposeTestRule<WeatherActivityScenarioRule<A>, A>

    fun launchForUserState(state: UserState, block: ComposeTestRule.() -> Unit)
}

class WeatherTestImpl<A : ComponentActivity>(
    clazz: Class<*>,
) : WeatherTest<A> {

    override val activityScenarioRule: WeatherActivityScenarioRule<A> =
        createWeatherActivityScenarioRule(
            intent = Intent(
                ApplicationProvider.getApplicationContext(),
                clazz,
            ),
        )

    override val integrationRule: WeatherIntegrationRule<A> =
        createWeatherIntegrationRule(
            rule = activityScenarioRule,
        )

    override val composeTestRule: AndroidComposeTestRule<WeatherActivityScenarioRule<A>, A> =
        createWeatherComposeTestRule(
            rule = activityScenarioRule,
        )

    override fun launchForUserState(state: UserState, block: ComposeTestRule.() -> Unit) {
        with(composeTestRule) {
            integrationRule.launchForState(state)
            block(this)
        }
    }
}

inline fun <reified A : ComponentActivity> createWeatherTest() =
    WeatherTestImpl<A>(clazz = A::class.java)
