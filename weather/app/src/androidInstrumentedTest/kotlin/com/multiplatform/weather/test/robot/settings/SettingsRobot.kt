@file:OptIn(ExperimentalTestApi::class)

package com.multiplatform.weather.test.robot.settings

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick

fun ComposeTestRule.onSettings(fn: SettingsRobot.() -> Unit) =
    fn(SettingsRobot(this))

class SettingsRobot(
    composeTestRule: ComposeTestRule,
) : ComposeTestRule by composeTestRule {

    init {
        waitUntilAtLeastOneExists(
            matcher = hasText("City"),
        )
    }

    fun temperatureUnitsDisplayed() {
        onNodeWithText("Temperature Unit:", useUnmergedTree = true)
            .assertIsDisplayed()

        onNodeWithText("°C", useUnmergedTree = true)
            .assertIsDisplayed()

        onNodeWithText("°F", useUnmergedTree = true)
            .assertIsDisplayed()
    }

    fun windSpeedUnitsDisplayed() {
        onNodeWithText("Wind Speed Unit:", useUnmergedTree = true)
            .assertIsDisplayed()

        onNodeWithText("km/h", useUnmergedTree = true)
            .assertIsDisplayed()
        onNodeWithText("m/s", useUnmergedTree = true)
            .assertIsDisplayed()
        onNodeWithText("mph", useUnmergedTree = true)
            .assertIsDisplayed()
        onNodeWithText("knots", useUnmergedTree = true)
            .assertIsDisplayed()
    }

    fun numberOfDaysDisplayed() {
        onNodeWithText("Number of Days:", useUnmergedTree = true)
            .assertIsDisplayed()

        onNodeWithTag("decrement_icon_button", useUnmergedTree = true)
            .assertIsDisplayed()
        onNodeWithTag("increment_icon_button", useUnmergedTree = true)
            .assertIsDisplayed()

        onNodeWithText("10", useUnmergedTree = true)
            .assertIsDisplayed()

        onNodeWithTag("decrement_icon_button", useUnmergedTree = true)
            .performClick()

        onNodeWithText("9", useUnmergedTree = true)
            .assertIsDisplayed()

        onNodeWithTag("increment_icon_button", useUnmergedTree = true)
            .performClick()

        onNodeWithText("10", useUnmergedTree = true)
            .assertIsDisplayed()
    }

    fun versionDisplayed() {
        onNodeWithText("v1.0.0 - mock", useUnmergedTree = true)
            .assertIsDisplayed()
    }

    fun clickBack() {
        onNodeWithTag("nav_bar_action", useUnmergedTree = true)
            .performClick()
    }
}
