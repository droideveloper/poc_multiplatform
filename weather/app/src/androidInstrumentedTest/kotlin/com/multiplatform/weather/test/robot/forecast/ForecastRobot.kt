@file:OptIn(ExperimentalTestApi::class)

package com.multiplatform.weather.test.robot.forecast

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasAnySibling
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick

fun ComposeTestRule.onForecast(fn: ForecastRobot.() -> Unit) =
    fn(ForecastRobot(this))

class ForecastRobot(
    composeTestRule: ComposeTestRule,
) : ComposeTestRule by composeTestRule {

    init {
        waitUntilAtLeastOneExists(
            matcher = hasText("Istanbul, Turkey"),
        )
    }

    fun dateCityDisplayed() {
        onNodeWithText("Today, 4 Oct 5:00 am", useUnmergedTree = true)
            .assertIsDisplayed()

        onNodeWithText("Istanbul, Turkey", useUnmergedTree = true)
            .assertIsDisplayed()
    }

    fun weatherDetailsDisplayed() {
        val matcher = hasText("16 °C")
            .and(
                hasAnySibling(
                    hasText("Partly cloudy"),
                ),
            )

        onNode(matcher, useUnmergedTree = true)
            .assertIsDisplayed()

        onNodeWithText("Partly cloudy", useUnmergedTree = true)
            .assertIsDisplayed()

        onNodeWithText("Felt", useUnmergedTree = true)
            .assertIsDisplayed()

        onNodeWithText("Wind", useUnmergedTree = true)
            .assertIsDisplayed()

        onNodeWithText("Sunrise", useUnmergedTree = true)
            .assertIsDisplayed()

        onNodeWithText("Sunset", useUnmergedTree = true)
            .assertIsDisplayed()

        onNodeWithText("Humidity", useUnmergedTree = true)
            .assertIsDisplayed()

        onNodeWithText("Pressure", useUnmergedTree = true)
            .assertIsDisplayed()
    }

    fun clickSetting() {
        onNodeWithTag("nav_bar_action", useUnmergedTree = true)
            .assertIsDisplayed()

        onNodeWithTag("nav_bar_action", useUnmergedTree = true)
            .performClick()
    }

    fun clickNext10Days() {
        onNodeWithText("Next 10 Days", useUnmergedTree = true)
            .assertIsDisplayed()

        onNodeWithText("Next 10 Days", useUnmergedTree = true)
            .performClick()
    }
}
