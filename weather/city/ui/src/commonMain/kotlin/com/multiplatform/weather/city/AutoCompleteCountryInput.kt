package com.multiplatform.weather.city

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.window.PopupProperties
import com.multiplatform.td.core.ui.input.InputError
import com.multiplatform.td.core.ui.input.InputValue
import com.multiplatform.td.core.ui.input.TextError
import com.multiplatform.td.core.ui.input.violatesRequired
import com.multiplatform.weather.core.ui.FwTextInput
import com.multiplatform.weather.core.ui.FwTheme

@Composable
internal fun FwAutoCompleteCountryInput(
    modifier: Modifier = Modifier,
    possibleValues: List<Country>,
    selectedValue: Country,
    onValueChange: (InputValue.Entered<Country>) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    label: String? = null,
    placeholder: String? = null,
    error: TextError = InputError.None,
    maxSearchResult: Int = 5,
    imeAction: ImeAction = ImeAction.Default,
) {
    var value: InputValue<String> by remember { mutableStateOf(InputValue.Default("")) }
    var isFocused by remember { mutableStateOf(false) }
    val suggestions = remember(value, possibleValues) {
        possibleValues.asSequence()
            .filter {
                it.selectFilterText().contains(value.value, ignoreCase = true) && selectedValue != it
            }
            .sortedBy { it.name }
            .take(maxSearchResult)
            .toList()
    }
    var expanded by remember(suggestions, value, isFocused) {
        mutableStateOf(isFocused && suggestions.isNotEmpty() && !value.violatesRequired())
    }
    val handleSuggestionSelected: (suggestion: Country) -> Unit = {
        onValueChange(InputValue.Entered(it))
        value = InputValue.Default("")
        expanded = false
    }
    val handleOnDismissRequested: () -> Unit = {
        expanded = false
    }
    Column(modifier = modifier) {
        FwTextInput(
            value = value,
            onValueChange = { value = it },
            modifier = Modifier.fillMaxWidth(),
            onFocusChange = {
                isFocused = it
            },
            label = label,
            placeholder = placeholder,
            error = error,
            keyboardType = keyboardType,
            maxLength = 48,
            imeAction = imeAction,
        )
        if (expanded) {
            DropDownMenu(handleOnDismissRequested, suggestions, handleSuggestionSelected)
        }
    }
}

@Composable
private fun ColumnScope.DropDownMenu(
    handleOnDismissRequested: () -> Unit,
    suggestions: List<Country>,
    handleSuggestionSelected: (suggestion: Country) -> Unit,
) {
    DropdownMenu(
        properties = PopupProperties(
            focusable = false,
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
        ),
        modifier = Modifier
            .weight(0.5f)
            .heightIn(max = FwTheme.dimens.standard256)
            .background(FwTheme.colors.whites.secondary)
            .testTag("text_input_suggestions"),
        expanded = true,
        onDismissRequest = handleOnDismissRequested,
    ) {
        suggestions.forEach {
            DropdownMenuItem(
                onClick = { handleSuggestionSelected(it) },
                text = { CountryView(it, onClick = handleSuggestionSelected) },
            )
        }
    }
}

internal fun Country.selectFilterText(): String = name.lowercase()
