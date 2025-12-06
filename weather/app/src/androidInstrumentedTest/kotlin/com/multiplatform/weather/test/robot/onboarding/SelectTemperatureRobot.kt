@file:OptIn(ExperimentalTestApi::class)

package com.multiplatform.weather.test.robot.onboarding

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick

fun ComposeTestRule.onTemperatureSelection(fn: TemperatureSelectionRobot.() -> Unit) =
    fn(TemperatureSelectionRobot(this))

class TemperatureSelectionRobot(
    composeTestRule: ComposeTestRule,
) : ComposeTestRule by composeTestRule {

    init {
        waitUntilAtLeastOneExists(
            hasText("Temperature"),
        )
    }

    fun titleAndMessageDisplayed() {
        onNodeWithText("Let's select a temperature unit", useUnmergedTree = true)
            .assertIsDisplayed()

        onNodeWithText("You could select your desired temperature unit below", useUnmergedTree = true)
            .assertIsDisplayed()
    }

    fun celsiusSelected() {
        val matcher = hasTestTag("segmented_button").and(
            other = hasAnyChild(hasText("°C")),
        )
        val segmentedButtonNode = onNode(matcher, useUnmergedTree = true)
        segmentedButtonNode.assertIsSelected()
    }

    fun clickContinue() {
        onNodeWithText("Continue").performClick()
    }
}
