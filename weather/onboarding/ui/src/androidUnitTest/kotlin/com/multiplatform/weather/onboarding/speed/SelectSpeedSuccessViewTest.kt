package com.multiplatform.weather.onboarding.speed

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.multiplatform.td.core.testing.AbstractAndroidUnitTest
import com.multiplatform.weather.core.measure.Speed
import com.multiplatform.weather.core.ui.FwTheme
import dev.mokkery.spy
import dev.mokkery.verify
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class SelectSpeedSuccessViewTest : AbstractAndroidUnitTest() {

    @Test
    fun testSuccessView() {
        val dispatch = spy<(SelectWindSpeedEvent) -> Unit>({})
        with(testRule) {
            setScreen {
                FwTheme {
                    SelectWindSpeedSuccessView(
                        isEnabled = true,
                        speed = Speed.KilometersPerHour,
                        dispatch = dispatch,
                    )
                }
            }

            onNodeWithTag("nav_bar_title", useUnmergedTree = true)
                .assertTextEquals("Wind Speed")

            onNodeWithTag("nav_bar_action", useUnmergedTree = true)
                .assertIsDisplayed()
            onNodeWithTag("nav_bar_action", useUnmergedTree = true)
                .performClick()

            verify { dispatch(SelectWindSpeedEvent.OnBackClicked) }

            onNodeWithText("Let's select a wind speed unit", useUnmergedTree = true)
                .assertIsDisplayed()
            onNodeWithText("You could select your desired wind speed unit below", useUnmergedTree = true)
                .assertIsDisplayed()

            val speeds = arrayOf(
                Speed.KilometersPerHour,
                Speed.MilesPerHour,
                Speed.Knots,
                Speed.MetersPerSecond,
            )
            speeds.forEach { speed ->
                onNodeWithText(speed.toString(), useUnmergedTree = true)
                    .assertIsDisplayed()
            }

            onNodeWithText(Speed.MilesPerHour.toString(), useUnmergedTree = true)
                .performClick()

            verify { dispatch(SelectWindSpeedEvent.OnChanged(Speed.MilesPerHour)) }

            onNodeWithText("Done")
                .assertIsDisplayed()
            onNodeWithText("Done")
                .performClick()

            verify { dispatch(SelectWindSpeedEvent.OnDoneClicked) }
        }
    }
}
