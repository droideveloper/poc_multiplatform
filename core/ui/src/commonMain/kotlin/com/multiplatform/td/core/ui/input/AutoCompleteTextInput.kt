package com.multiplatform.td.core.ui.input

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.multiplatform.td.core.ui.TdTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TdAutoCompleteTextInput(
    modifier: Modifier = Modifier,
    value: TextValue = InputValue.Default(""),
    possibleValues: List<String>,
    onValueChange: (InputValue.Entered<String>) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    label: String? = null,
    placeholder: String? = null,
    error: TextError = InputError.None,
    imeAction: ImeAction = ImeAction.Default,
) {
    var isFocused by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("") }
    val suggestions = remember(value, possibleValues) {
        possibleValues.filter { it.contains(value.value, ignoreCase = true) }
    }
    var expanded by remember(suggestions, value, isFocused) {
        mutableStateOf(
            isFocused && suggestions.isNotEmpty() && !value.violatesRequired() && selectedItem != value.value
        )
    }
    val handleSuggestionSelected: (suggestion: String) -> Unit = {
        if (value.value != it) {
            selectedItem = it
            onValueChange(InputValue.Entered(it))
        }
        expanded = false
    }
    val handleOnDismissRequested: () -> Unit = {
        expanded = false
    }

    Column(modifier = modifier) {
        TdTextInput(
            value = value,
            onValueChange = { onValueChange(it) },
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
        if (expanded)
            DropDownMenu(handleOnDismissRequested, suggestions, handleSuggestionSelected)
    }
}

@Composable
private fun DropDownMenu(
    handleOnDismissRequested: () -> Unit,
    suggestions: List<String>,
    handleSuggestionSelected: (suggestion: String) -> Unit,
) {
    DropdownMenu(
        properties = PopupProperties(
            focusable = false,
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        ),
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = TdTheme.dimens.standard256)
            .background(TdTheme.colors.whites.primary)
            .testTag("text_input_suggestions"),
        expanded = true,
        onDismissRequest = handleOnDismissRequested
    ) {
        suggestions.forEach {
            DropdownMenuItem(
                onClick = { handleSuggestionSelected(it) },
                text = { Text(text = it) },
            )
        }
    }
}

@Preview()
@Composable
private fun EmptyInput() = TdTheme {
    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp)
    ) {
        TdAutoCompleteTextInput(
            value = InputValue.Default("Te"),
            onValueChange = {},
            label = "Label",
            placeholder = "Type here",
            possibleValues = listOf("Test 1", "Test 2", "Test 3"),
        )
    }
}

@Preview()
@Composable
private fun InputWithValue() = TdTheme {
    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp)
    ) {
        TdAutoCompleteTextInput(
            value = InputValue.Entered("Text entered"),
            onValueChange = {},
            label = "Label",
            placeholder = "Type here",
            possibleValues = listOf("Test 1", "Test 2", "Test 3"),
        )
    }
}

@Preview()
@Composable
private fun InputWithError() = TdTheme {
    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp)
    ) {
        TdAutoCompleteTextInput(
            value = InputValue.Entered("Text entered"),
            onValueChange = {},
            label = "Label",
            placeholder = "Type here",
            possibleValues = listOf("Test 1", "Test 2", "Test 3"),
            error = InputError.WithMessage("Please enter the correct value here"),
        )
    }
}