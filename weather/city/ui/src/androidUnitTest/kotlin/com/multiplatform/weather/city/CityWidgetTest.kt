package com.multiplatform.weather.city

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.multiplatform.td.core.testing.AbstractAndroidUnitTest
import com.multiplatform.weather.core.ui.FwTheme
import dev.mokkery.spy
import dev.mokkery.verify
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class CityWidgetTest : AbstractAndroidUnitTest() {

    @Test
    fun testLoadingView() {
        with(testRule) {
            setScreen { CityLoadingView() }

            val matcher = SemanticsMatcher.expectValue(
                key = SemanticsPropertyKey("fraction"),
                expectedValue = 0.50f,
            )

            val nodes = onAllNodesWithTag("shimmer_space", useUnmergedTree = true)

            val half = nodes.filterToOne(matcher)
            half.isDisplayed()

            nodes.assertCountEquals(4)
        }
    }

    @Test
    fun testFailureView() {
        val dispatch = spy<(CityEvent) -> Unit>({})
        with(testRule) {
            setScreen {
                CityFailureView(
                    modifier = Modifier.fillMaxWidth(),
                    uiState = UiState.Failure.Text("Sorry we could not fetch cities now!"),
                    dispatch = dispatch,
                )
            }

            onNodeWithText("Failed to load cities", useUnmergedTree = true).isDisplayed()
            onNodeWithText("Sorry we could not fetch cities now!", useUnmergedTree = true).isDisplayed()

            onNodeWithText("Try Again", useUnmergedTree = true).isDisplayed()
            onNodeWithText("Try Again", useUnmergedTree = true).performClick()

            verify { dispatch(CityEvent.OnTryAgainClicked) }
        }
    }

    @Test
    fun testSuccessView() {
        val dispatch = spy<(CityEvent) -> Unit>({})
        val onCitySelect = spy<(City) -> Unit>({})
        val onCityRemoved = spy<(City) -> Unit>({})

        val cities = listOf(
            city,
            city.copy(id = 1, name = "Ankara", displayName = "Ankara"),
            city.copy(id = 2, name = "Adana", displayName = "Adana"),
            city.copy(id = 3, name = "Izmir", displayName = "Izmir"),
        )

        with(testRule) {
            setScreen {
                CitySuccessView(
                    allowLastSelectionRemoval = false,
                    cities = cities,
                    selectedCities = emptyList(),
                    onCitySelect = onCitySelect,
                    onCityRemoved = {},
                    dispatch = dispatch,
                )
            }

            onNodeWithTag("text_input").isDisplayed()
            onNodeWithTag("text_input").performTextInput("Istanbul")

            onNodeWithTag("text_input_suggestions").isDisplayed()

            onNodeWithText("Istanbul, TR", useUnmergedTree = true).isDisplayed()
            onNodeWithText("Istanbul, TR", useUnmergedTree = true).performClick()

            verify {
                dispatch(CityEvent.Operation.Add(city))
                onCitySelect(city)
            }
        }
    }

    @Test
    fun testSuccessViewWithSelection() {
        val dispatch = spy<(CityEvent) -> Unit>({})
        val onCityRemoved = spy<(City) -> Unit>({})

        val cities = listOf(
            city,
            city.copy(id = 1, name = "Ankara", displayName = "Ankara"),
            city.copy(id = 2, name = "Adana", displayName = "Adana"),
            city.copy(id = 3, name = "Izmir", displayName = "Izmir"),
        )

        with(testRule) {
            setScreen {
                CitySuccessView(
                    allowLastSelectionRemoval = true,
                    cities = cities,
                    selectedCities = listOf(city),
                    onCitySelect = {},
                    onCityRemoved = onCityRemoved,
                    dispatch = dispatch,
                )
            }

            onNodeWithText("Istanbul, TR", useUnmergedTree = true).isDisplayed()
            onNodeWithText("Istanbul, TR", useUnmergedTree = true).performClick()

            verify {
                dispatch(CityEvent.Operation.Remove(city))
                onCityRemoved(city)
            }
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
