package com.multiplatform.weather.onboarding.city

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
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
internal class SelectCitySuccessViewTest : AbstractAndroidUnitTest() {

    @Test
    fun testSuccessView() {
        val dispatch = spy<(SelectCityEvent) -> Unit>({})
        with(testRule) {
            setScreen {
                SelectCitySuccessView(
                    isEnabled = true,
                    dispatch = dispatch,
                    widget = @Composable {
                        Text(text = "Widget Placeholder")
                    },
                )
            }

            onNodeWithTag("nav_bar_title", useUnmergedTree = true).assertTextEquals("City")
            onNodeWithText("Let's select a city or more", useUnmergedTree = true).isDisplayed()
            onNodeWithText("You could select more than one city and\nhave swipe action to see weather on that city", useUnmergedTree = true).isDisplayed()

            onNodeWithText("Widget Placeholder", useUnmergedTree = true).isDisplayed()

            onNodeWithText("Continue").isDisplayed()
            onNodeWithText("Continue").performClick()

            verify { dispatch(SelectCityEvent.OnContinueClicked) }
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
