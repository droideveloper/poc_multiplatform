package com.multiplatform.weather.city

import androidx.compose.foundation.layout.Row
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
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
                FwTheme {
                    Row {
                        CountryFlag(
                            countryCode = countryCode,
                            countryFlagSize = CountryFlagSize.Large,
                        )
                    }
                }
                // it needs to be in composable
                flagText = countryCode.selectFlag()
            }

            onNodeWithTag("text_flag")
                .assertIsDisplayed()
            onNodeWithTag("text_flag")
                .assertTextEquals(flagText)
        }
    }
}
