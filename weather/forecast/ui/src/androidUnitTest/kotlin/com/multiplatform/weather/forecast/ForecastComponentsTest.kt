package com.multiplatform.weather.forecast

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.multiplatform.td.core.testing.AbstractAndroidUnitTest
import com.multiplatform.weather.core.measure.Temperature
import com.multiplatform.weather.core.measure.TemperatureAmount
import com.multiplatform.weather.core.ui.FwTheme
import com.multiplatform.weather.forecast.today.WeatherDescriptionState
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class ForecastComponentsTest : AbstractAndroidUnitTest() {

    @Test
    fun testWeatherDescription() {
        with(testRule) {
            setScreen {
                WeatherDescription(
                    state = WeatherDescriptionState(
                        temperature = TemperatureAmount(
                            amount = 32.5,
                            unit = Temperature.Celsius,
                        ),
                        weatherCode = WeatherCode.getOrThrow(22),
                    ),
                    unit = Temperature.Celsius,
                )
            }

            onNodeWithText("Overcast").isDisplayed()
            onNodeWithText("33 °C").isDisplayed()
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
