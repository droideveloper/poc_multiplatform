package com.multiplatform.weather.city

import androidx.compose.ui.test.assertIsDisplayed
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
                FwTheme {
                    CityView(
                        city = city,
                        onClick = onClick,
                    )
                }
            }

            onNodeWithText("Istanbul, TR")
                .assertIsDisplayed()
            onNodeWithText("Istanbul, TR")
                .performClick()

            verify { onClick(city) }
        }
    }
}
