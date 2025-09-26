package com.multiplatform.weather.settings

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.multiplatform.td.core.environment.AppVersion
import com.multiplatform.td.core.testing.AbstractAndroidUnitTest
import com.multiplatform.weather.core.measure.Speed
import com.multiplatform.weather.core.measure.Temperature
import com.multiplatform.weather.core.ui.FwTheme
import dev.mokkery.spy
import dev.mokkery.verify
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class SettingsSuccessViewTest : AbstractAndroidUnitTest() {

    @Test
    fun testSuccessView() {
        val dispatch = spy<(SettingsEvent) -> Unit>({})
        with(testRule) {
            setScreen {
                SettingsSuccessView(
                    settings = Settings.Defaults.copy(days = 5),
                    version = AppVersion("1,0,0"),
                    dispatch = dispatch,
                ) {
                    Text("Widget Placeholder")
                }
            }

            onNodeWithTag("nav_bar_title", useUnmergedTree = true).assertTextEquals("Settings")

            onNodeWithTag("nav_bar_action", useUnmergedTree = true).isDisplayed()
            onNodeWithTag("nav_bar_action", useUnmergedTree = true).performClick()

            verify { dispatch(SettingsEvent.OnBackClicked) }

            val speeds = arrayOf(
                Speed.KilometersPerHour,
                Speed.MilesPerHour,
                Speed.Knots,
                Speed.MetersPerSecond,
            )
            speeds.forEach { speed ->
                onNodeWithText(speed.toString(), useUnmergedTree = true).isDisplayed()
            }
            onNodeWithText(Speed.MilesPerHour.toString(), useUnmergedTree = true).performClick()

            verify { dispatch(SettingsEvent.OnSpeed.Change(Speed.MilesPerHour)) }

            val temperatures = arrayOf(Temperature.Celsius, Temperature.Fahrenheit)
            temperatures.forEach { temperature ->
                onNodeWithText(temperature.toString(), useUnmergedTree = true).isDisplayed()
            }
            onNodeWithText(Temperature.Fahrenheit.toString(), useUnmergedTree = true).performClick()

            verify { dispatch(SettingsEvent.OnTemperature.Change(Temperature.Fahrenheit)) }

            onNodeWithTag("increment_icon_button", useUnmergedTree = true).isDisplayed()
            onNodeWithTag("increment_icon_button", useUnmergedTree = true).performClick()

            verify { dispatch(SettingsEvent.OnDays.Increment) }

            onNodeWithTag("decrement_icon_button", useUnmergedTree = true).isDisplayed()
            onNodeWithTag("decrement_icon_button", useUnmergedTree = true).performClick()

            verify { dispatch(SettingsEvent.OnDays.Decrement) }

            onNodeWithText("1.0.0", useUnmergedTree = true).isDisplayed()
            onNodeWithText("Widget Placeholder", useUnmergedTree = true).isDisplayed()
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
