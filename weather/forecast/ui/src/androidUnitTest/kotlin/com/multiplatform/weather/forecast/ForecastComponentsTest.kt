package com.multiplatform.weather.forecast

import androidx.compose.ui.test.assertIsDisplayed
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
                FwTheme {
                    WeatherDescription(
                        state = WeatherDescriptionState(
                            temperature = TemperatureAmount(
                                amount = 32.5,
                                unit = Temperature.Celsius,
                            ),
                            weatherCode = WeatherCode.getOrThrow(3),
                        ),
                        unit = Temperature.Celsius,
                    )
                }
            }

            onNodeWithText("Overcast")
                .assertIsDisplayed()
            onNodeWithText("33 °C")
                .assertIsDisplayed()
        }
    }
}
