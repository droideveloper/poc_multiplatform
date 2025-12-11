@file:OptIn(ExperimentalTestApi::class)

package com.multiplatform.weather.test.robot.onboarding

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput

fun ComposeTestRule.onCitySelection(fn: CitySelectionRobot.() -> Unit) =
    fn(CitySelectionRobot(this))

class CitySelectionRobot(
    composeTestRule: ComposeTestRule,
) : ComposeTestRule by composeTestRule {

    init {
        waitUntilAtLeastOneExists(hasText("Select a city"))
    }

    fun titleAndMessageDisplayed() {
        onNodeWithText("Let's select a city or more", useUnmergedTree = true)
            .assertIsDisplayed()

        onNodeWithText("You could select more than one city and\nhave swipe action to see weather on that city", useUnmergedTree = true)
            .assertIsDisplayed()
    }

    fun cityTextDisplayed() {
        onNodeWithText("Country", useUnmergedTree = true)
            .assertIsDisplayed()

        onNodeWithText("Select a city", useUnmergedTree = true)
            .assertIsDisplayed()
    }

    fun selectCity() {
        val cityInputNode = onNode(
            matcher = hasParent(hasTestTag("city_input"))
                .and(
                    other = hasTestTag("text_input"),
                ),
            useUnmergedTree = true,
        )

        cityInputNode.performTextInput("Istanbul")
        cityInputNode.performImeAction()

        waitUntilAtLeastOneExists(
            hasText("Istanbul, TR"),
        )

        onNodeWithText("Istanbul, TR", useUnmergedTree = true)
            .isDisplayed()

        onNodeWithText("Istanbul, TR", useUnmergedTree = true)
            .performClick()
    }

    fun clickContinue() {
        onNodeWithText("Continue").performClick()
    }
}
