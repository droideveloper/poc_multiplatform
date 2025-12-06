@file:OptIn(ExperimentalTestApi::class)

package com.multiplatform.weather.city

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.multiplatform.td.core.testing.AbstractAndroidUnitTest
import com.multiplatform.td.core.ui.input.InputValue
import com.multiplatform.weather.core.ui.FwTheme
import dev.mokkery.spy
import dev.mokkery.verify
import org.jetbrains.compose.resources.stringResource
import org.junit.Test
import org.junit.runner.RunWith
import tdmultiplatform.weather.city.ui.generated.resources.Res
import tdmultiplatform.weather.city.ui.generated.resources.city_ui_select_country_title

@RunWith(AndroidJUnit4::class)
internal class AutoCompleteCountryInputTest : AbstractAndroidUnitTest() {

    @Test
    fun testAutoCompleteView() {
        val onCountrySelect = spy<(InputValue.Entered<Country>) -> Unit>({})
        val countries = listOf(
            Country(name = "Turkey", CountryCode.getOrThrow("TR")),
            Country(name = "United State", CountryCode.getOrThrow("US")),
            Country(name = "United Kingdom", CountryCode.getOrThrow("GB")),
        )

        with(testRule) {
            var flag = ""
            setScreen {
                FwTheme {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = FwTheme.dimens.standard8),
                    ) {
                        FwAutoCompleteCountryInput(
                            modifier = Modifier.fillMaxWidth(),
                            possibleValues = countries,
                            selectedValue = countries.last(),
                            onValueChange = onCountrySelect,
                            label = stringResource(Res.string.city_ui_select_country_title),
                            placeholder = countries.last().code.selectFlag() + selectCountry(countries.last()),
                        )
                        flag = countries.first().code.selectFlag()
                    }
                }
            }

            onNodeWithTag("text_input")
                .assertIsDisplayed()
            onNodeWithTag("text_input")
                .performTextInput("Turkey")

            onNodeWithTag("text_input_suggestions")
                .assertIsDisplayed()

            onNodeWithText(flag, useUnmergedTree = true)
                .assertIsDisplayed()
            onNodeWithText(flag, useUnmergedTree = true)
                .performClick()

            verify {
                onCountrySelect(InputValue.Entered<Country>(countries.first()))
            }
        }
    }
}
