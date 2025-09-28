package com.multiplatform.weather.city

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.multiplatform.td.core.testing.AbstractAndroidUnitTest
import com.multiplatform.weather.core.ui.FwTheme
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class CountryFlagTest : AbstractAndroidUnitTest() {

    @Test
    fun testCountryFlag() {
        val countryCode = CountryCode.getOrThrow("TR")
        var flagText = ""
        with(testRule) {
            setScreen {
                Row {
                    CountryFlag(
                        countryCode = countryCode,
                        countryFlagSize = CountryFlagSize.Large,
                    )
                }
                // it needs to be in composable
                flagText = countryCode.selectFlag()
            }

            onNodeWithTag("text_flag").isDisplayed()
            onNodeWithTag("text_flag").assertTextEquals(flagText)
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
