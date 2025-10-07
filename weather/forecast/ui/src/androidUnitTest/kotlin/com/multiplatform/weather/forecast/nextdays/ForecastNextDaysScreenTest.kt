package com.multiplatform.weather.forecast.nextdays

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onAllNodesWithText
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
internal class ForecastNextDaysScreenTest : AbstractAndroidUnitTest() {

    @Test
    fun testTodayWeatherDescription() {
        with(testRule) {
            setScreen {
                TodayWeatherDescription(
                    state = weatherDescriptionState,
                    city = city,
                    unit = Temperature.Celsius,
                )
            }

            onNodeWithText("32 °C", useUnmergedTree = true).isDisplayed()
            onNodeWithText("Clear sky", useUnmergedTree = true).isDisplayed()

            onNodeWithText("Today", useUnmergedTree = true).isDisplayed()
            onNodeWithText("Istanbul, Turkey", useUnmergedTree = true).isDisplayed()
        }
    }

    @Test
    fun testNthDayWeatherDescription() {
        with(testRule) {
            setScreen {
                NthDayWeatherDescription(
                    state = weatherNextDayDescriptionState,
                    unit = Temperature.Celsius,
                )
            }

            onNodeWithText("Tuesday", useUnmergedTree = true).isDisplayed()
            onNodeWithText("29 Jul", useUnmergedTree = true).isDisplayed()

            onNodeWithText("Clear sky", useUnmergedTree = true).isDisplayed()

            onNodeWithText("32 °C", useUnmergedTree = true).isDisplayed()
        }
    }

    @Test
    fun testTodayHourlyDescription() {
        with(testRule) {
            setScreen {
                TodayHourlyDescription(
                    state = weatherHourlyDescriptionState,
                    unit = Temperature.Celsius,
                )
            }

            onNodeWithText("4 AM", useUnmergedTree = true).isDisplayed()
            onNodeWithText("5 AM", useUnmergedTree = true).isDisplayed()
            onNodeWithText("6 AM", useUnmergedTree = true).isDisplayed()
            onNodeWithText("7 AM", useUnmergedTree = true).isDisplayed()
            onNodeWithText("8 AM", useUnmergedTree = true).isDisplayed()
            onNodeWithText("9 AM", useUnmergedTree = true).isDisplayed()

            onAllNodesWithText("32 °C", useUnmergedTree = true).assertCountEquals(6)
        }
    }

    @Test
    fun testForecastNextDaysSuccessView() {
        val dispatch = spy<(ForecastNextDaysEvent) -> Unit>({})
        with(testRule) {
            setScreen {
                ForecastNextDaysSuccessView(
                    state = forecastNextDayState,
                    dispatch = dispatch,
                )
            }

            onNodeWithText("Next 10 days", useUnmergedTree = true).isDisplayed()
            onNodeWithTag("nav_bar_action").isDisplayed()
            onNodeWithTag("nav_bar_action").performClick()

            verify { dispatch(ForecastNextDaysEvent.OnBackClicked) }
        }
    }

    private fun ComposeTestRule.setScreen(content: @Composable () -> Unit) {
        if (this is ComposeContentTestRule) {
            setContent { FwTheme { content() } }
        }
    }
}
