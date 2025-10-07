package com.multiplatform.weather.forecast.today

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.multiplatform.td.core.testing.AbstractAndroidUnitTest
import com.multiplatform.weather.core.ui.FwTheme
import com.multiplatform.weather.forecast.nextdays.city
import dev.mokkery.spy
import dev.mokkery.verify
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class ForecastScreenTest : AbstractAndroidUnitTest() {

    @Test
    fun testForecastSuccessView() {
        val dispatch = spy<(ForecastEvent) -> Unit>({})
        with(testRule) {
            setScreen {
                ForecastSuccessView(
                    state = forecastState,
                    dispatch = dispatch,
                )
            }

            onNodeWithText("Today, 1 Jul 9:30 am", useUnmergedTree = true).isDisplayed()
            onNodeWithText("Istanbul, Turkey", useUnmergedTree = true).isDisplayed()

            onNodeWithTag("nav_bar_action", useUnmergedTree = true).isDisplayed()
            onNodeWithTag("nav_bar_action", useUnmergedTree = true).performClick()

            verify { dispatch(ForecastEvent.OnSettingsClicked) }

            onNodeWithTag("button_text").isDisplayed()
            onNodeWithTag("button_text").performScrollTo()
            onNodeWithTag("button_text").performClick()

            verify { dispatch(ForecastEvent.OnNextDaysClicked(city)) }
        }
    }

    private fun ComposeTestRule.setScreen(content: @Composable () -> Unit) {
        if (this is ComposeContentTestRule) {
            setContent { FwTheme { content() } }
        }
    }
}
