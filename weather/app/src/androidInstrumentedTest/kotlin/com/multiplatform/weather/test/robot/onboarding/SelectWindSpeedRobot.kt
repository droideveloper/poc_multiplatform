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

fun ComposeTestRule.onWindSpeedSelection(fn: WindSpeedSelectionRobot.() -> Unit) =
    fn(WindSpeedSelectionRobot(this))

class WindSpeedSelectionRobot(
    composeTestRule: ComposeTestRule,
) : ComposeTestRule by composeTestRule {

    init {
        waitUntilAtLeastOneExists(
            hasText("Wind Speed"),
        )
    }

    fun titleAndMessageDisplayed() {
        onNodeWithText("Let's select a wind speed unit", useUnmergedTree = true)
            .assertIsDisplayed()

        onNodeWithText("You could select your desired wind speed unit below", useUnmergedTree = true)
            .assertIsDisplayed()
    }

    fun kmhSelected() {
        val matcher = hasTestTag("segmented_button").and(
            other = hasAnyChild(hasText("km/h")),
        )
        val segmentedButtonNode = onNode(matcher, useUnmergedTree = true)
        segmentedButtonNode.assertIsSelected()
    }

    fun clickDone() {
        onNodeWithText("Done").performClick()
    }
}
