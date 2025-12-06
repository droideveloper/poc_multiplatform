package com.multiplatform.weather.city

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
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
            setScreen { FwTheme { CityLoadingView() } }

            val nodes = onAllNodesWithTag("shimmer_space", useUnmergedTree = true)

            nodes.assertCountEquals(4)
        }
    }

    @Test
    fun testFailureView() {
        val dispatch = spy<(CityEvent) -> Unit>({})
        with(testRule) {
            setScreen {
                FwTheme {
                    CityFailureView(
                        modifier = Modifier.fillMaxWidth(),
                        uiState = UiState.Failure.Text("Sorry we could not fetch cities now!"),
                        dispatch = dispatch,
                    )
                }
            }

            onNodeWithText("Failed to load cities", useUnmergedTree = true)
                .assertIsDisplayed()
            onNodeWithText("Sorry we could not fetch cities now!", useUnmergedTree = true)
                .assertIsDisplayed()

            onNodeWithText("Try Again", useUnmergedTree = true)
                .assertIsDisplayed()
            onNodeWithText("Try Again", useUnmergedTree = true)
                .performClick()

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
                FwTheme {
                    CitySuccessView(
                        allowLastSelectionRemoval = false,
                        cities = cities,
                        selectedCities = emptyList(),
                        onCitySelect = onCitySelect,
                        onCityRemoved = onCityRemoved,
                        dispatch = dispatch,
                    )
                }
            }

            onNodeWithTag("text_input")
                .assertIsDisplayed()
            onNodeWithTag("text_input")
                .performTextInput("Istanbul")

            onNodeWithTag("text_input_suggestions")
                .assertIsDisplayed()

            onNodeWithText("Istanbul, TR", useUnmergedTree = true)
                .assertIsDisplayed()
            onNodeWithText("Istanbul, TR", useUnmergedTree = true)
                .performClick()

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
                FwTheme {
                    CitySuccessView(
                        allowLastSelectionRemoval = true,
                        cities = cities,
                        selectedCities = listOf(city),
                        onCitySelect = {},
                        onCityRemoved = onCityRemoved,
                        dispatch = dispatch,
                    )
                }
            }

            onNodeWithText("Istanbul, TR", useUnmergedTree = true)
                .assertIsDisplayed()
            onNodeWithText("Istanbul, TR", useUnmergedTree = true)
                .performClick()

            verify {
                dispatch(CityEvent.Operation.Remove(city))
                onCityRemoved(city)
            }
        }
    }
}
