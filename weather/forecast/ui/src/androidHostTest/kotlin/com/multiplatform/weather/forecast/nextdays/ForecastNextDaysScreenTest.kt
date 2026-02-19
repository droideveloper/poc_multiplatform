package com.multiplatform.weather.forecast.nextdays

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
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
                FwTheme {
                    TodayWeatherDescription(
                        state = weatherDescriptionState,
                        city = city,
                        unit = Temperature.Celsius,
                    )
                }
            }

            onNodeWithText("32 °C", useUnmergedTree = true)
                .assertIsDisplayed()
            onNodeWithText("Clear sky", useUnmergedTree = true)
                .assertIsDisplayed()

            onNodeWithText("Today", useUnmergedTree = true)
                .assertIsDisplayed()
            onNodeWithText("Istanbul, Turkey", useUnmergedTree = true)
                .assertIsDisplayed()
        }
    }

    @Test
    fun testNthDayWeatherDescription() {
        with(testRule) {
            setScreen {
                FwTheme {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        NthDayWeatherDescription(
                            state = weatherNextDayDescriptionState,
                            unit = Temperature.Celsius,
                        )
                    }
                }
            }

            onNodeWithText("Tuesday\n29 Jul", useUnmergedTree = true)
                .assertIsDisplayed()

            onNodeWithText("Clear sky", useUnmergedTree = true)
                .assertIsDisplayed()

            onNodeWithText("32 °C", useUnmergedTree = true)
                .assertIsDisplayed()
        }
    }

    @Test
    fun testTodayHourlyDescription() {
        with(testRule) {
            setScreen {
                FwTheme {
                    TodayHourlyDescription(
                        state = weatherHourlyDescriptionState,
                        unit = Temperature.Celsius,
                    )
                }
            }

            onNodeWithText("4 AM", useUnmergedTree = true)
                .assertIsDisplayed()
            onNodeWithText("5 AM", useUnmergedTree = true)
                .assertIsDisplayed()
            onNodeWithText("6 AM", useUnmergedTree = true)
                .assertIsDisplayed()
            onNodeWithText("7 AM", useUnmergedTree = true)
                .assertIsDisplayed()

            onNodeWithText("8 AM", useUnmergedTree = true)
                .performScrollTo()
            onNodeWithText("8 AM", useUnmergedTree = true)
                .assertIsDisplayed()

            onNodeWithText("9 AM", useUnmergedTree = true)
                .performScrollTo()
            onNodeWithText("9 AM", useUnmergedTree = true)
                .assertIsDisplayed()

            onAllNodesWithText("32 °C", useUnmergedTree = true)
                .assertCountEquals(7)
        }
    }

    @Test
    fun testForecastNextDaysSuccessView() {
        val dispatch = spy<(ForecastNextDaysEvent) -> Unit>({})
        with(testRule) {
            setScreen {
                FwTheme {
                    ForecastNextDaysSuccessView(
                        state = forecastNextDayState,
                        dispatch = dispatch,
                    )
                }
            }

            onNodeWithText("Next 10 Days", useUnmergedTree = true)
                .assertIsDisplayed()
            onNodeWithTag("nav_bar_action")
                .assertIsDisplayed()
            onNodeWithTag("nav_bar_action")
                .performClick()

            verify { dispatch(ForecastNextDaysEvent.OnBackClicked) }
        }
    }
}
