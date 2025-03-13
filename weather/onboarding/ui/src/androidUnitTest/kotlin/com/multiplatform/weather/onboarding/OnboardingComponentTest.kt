package com.multiplatform.weather.onboarding

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
internal class OnboardingComponentTest : AbstractAndroidUnitTest() {

    @Test
    fun testContinueButton() {
        with(testRule) {
            val onClick = spy<() -> Unit>({})
            setScreen { FwTheme { ContinueButton(onClick = onClick) } }

            onNodeWithTag("button_text", useUnmergedTree = true).assertTextEquals("Continue")
            onNodeWithTag("button_continue").performClick()

            verify { onClick() }
        }
    }

    @Test
    fun testDoneButton() {
        with(testRule) {
            val onClick = spy<() -> Unit>({})
            setScreen { FwTheme { DoneButton(onClick = onClick) } }
            onNodeWithTag("button_text", useUnmergedTree = true).assertTextEquals("Done")
            onNodeWithTag("button_done").performClick()

            verify { onClick() }
        }
    }

    @Test
    fun testOnboardingLayout() {
        with(testRule) {
            setScreen {
                FwTheme {
                    OnboardingLayout(
                        title = "Title",
                        body = "Body",
                        content = {},
                        action = {},
                    )
                }
            }

            onNodeWithText("Title", useUnmergedTree = true).isDisplayed()
            onNodeWithText("Body", useUnmergedTree = true).isDisplayed()
        }
    }

    @Test
    fun testOnboardingFailureView() {
        with(testRule) {
            val onClick = spy<() -> Unit>({})
            val state = UiState.Failure.Text("Error body")
            setScreen {
                FwTheme { OnboardingFailureView(state, onClick) }
            }

            onNodeWithText("Encountered an error", useUnmergedTree = true).isDisplayed()
            onNodeWithText("Error body", useUnmergedTree = true).isDisplayed()

            onNodeWithText("Try again", useUnmergedTree = true).isDisplayed()
            onNodeWithText("Try again").performClick()

            verify { onClick() }
        }
    }

    private fun ComposeTestRule.setScreen(content: @Composable () -> Unit) {
        if (this is ComposeContentTestRule) {
            setContent { content() }
        }
    }
}