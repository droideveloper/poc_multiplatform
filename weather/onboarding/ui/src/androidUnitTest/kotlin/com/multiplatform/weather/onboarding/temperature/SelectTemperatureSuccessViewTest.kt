package com.multiplatform.weather.onboarding.temperature

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.multiplatform.td.core.testing.AbstractAndroidUnitTest
import com.multiplatform.weather.core.measure.Temperature
import com.multiplatform.weather.core.ui.FwTheme
import dev.mokkery.spy
import dev.mokkery.verify
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class SelectTemperatureSuccessViewTest : AbstractAndroidUnitTest() {

    @Test
    fun testSuccessView() {
        with(testRule) {
            val dispatch: (SelectTemperatureEvent) -> Unit = spy({})
            setScreen {
                SelectTemperatureSuccessView(
                    isEnabled = true,
                    temperature = Temperature.Celsius,
                    dispatch = dispatch,
                )
            }

            onNodeWithTag("nav_bar_title", useUnmergedTree = true).assertTextEquals("Temperature")
            onNodeWithText("Let's select a temperature unit", useUnmergedTree = true).isDisplayed()
            onNodeWithText("You could select your desired temperature unit below", useUnmergedTree = true).isDisplayed()
            val temperatures = arrayOf(Temperature.Celsius, Temperature.Fahrenheit)
            temperatures.forEach { temperature ->
                onNodeWithText(temperature.toString(), useUnmergedTree = true).isDisplayed()
            }

            onNodeWithText("Continue").isDisplayed()
            onNodeWithText("Continue").performClick()

            verify { dispatch(SelectTemperatureEvent.OnContinueClicked) }
        }
    }

    private fun ComposeTestRule.setScreen(content: @Composable () -> Unit) {
        if (this is ComposeContentTestRule) {
            setContent {
                FwTheme {
                    content()
                }
            }
        }
    }
}