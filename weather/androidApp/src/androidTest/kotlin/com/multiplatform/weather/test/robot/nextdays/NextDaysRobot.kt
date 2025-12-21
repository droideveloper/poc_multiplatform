@file:OptIn(ExperimentalTestApi::class)

package com.multiplatform.weather.test.robot.nextdays

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasAnySibling
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick

fun ComposeTestRule.onNextDays(fn: NextDaysRobot.() -> Unit) =
    fn(NextDaysRobot(this))

class NextDaysRobot(
    composeTestRule: ComposeTestRule,
) : ComposeTestRule by composeTestRule {

    init {
        waitUntilAtLeastOneExists(
            matcher = hasText("Next 10 Days"),
        )
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

        onNodeWithText("Today", useUnmergedTree = true)
            .assertIsDisplayed()

        onNodeWithText("Istanbul, Turkey", useUnmergedTree = true)
            .assertIsDisplayed()
    }

    fun nextDaysFirstDisplayed() {
        val matcher = hasText("14 °C")
            .and(
                hasAnySibling(
                    hasText("Foggy"),
                ),
            )

        onNode(matcher, useUnmergedTree = true)
            .assertIsDisplayed()

        onNodeWithText("Sunday\n5 Oct", useUnmergedTree = true)
            .assertIsDisplayed()
    }

    fun nextDaysLastDisplayed() {
        val matcher = hasText("16 °C")
            .and(
                hasAnySibling(
                    hasText("Slight rain showers"),
                ),
            )

        onNode(matcher, useUnmergedTree = true)
            .assertIsDisplayed()

        onNodeWithText("Monday\n6 Oct", useUnmergedTree = true)
            .assertIsDisplayed()
    }

    fun clickBack() {
        onNodeWithTag("nav_bar_action", useUnmergedTree = true)
            .performClick()
    }
}
