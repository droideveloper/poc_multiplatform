package com.multiplatform.weather.city

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.multiplatform.td.core.testing.AbstractAndroidUnitTest
import com.multiplatform.weather.core.ui.FwTheme
import dev.mokkery.spy
import dev.mokkery.verify
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class CityViewTest : AbstractAndroidUnitTest() {

    @Test
    fun testCityView() {
        val onClick = spy<(City) -> Unit>({})
        with(testRule) {
            setScreen {
                CityView(
                    city = city,
                    onClick = onClick,
                )
            }

            onNodeWithText("Istanbul, TR").isDisplayed()
            onNodeWithText("Istanbul, TR").performClick()

            verify { onClick(city) }
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
