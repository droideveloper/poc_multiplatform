package com.multiplatform.weather.city

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
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
import org.jetbrains.compose.resources.vectorResource
import tdmultiplatform.weather.city.ui.generated.resources.Res
import tdmultiplatform.weather.city.ui.generated.resources.ic_delete

@Composable
internal fun FwAutoCompleteCityInput(
    modifier: Modifier = Modifier,
    possibleValues: List<City>,
    selectedValues: List<City>,
    onValueChange: (InputValue.Entered<City>) -> Unit,
    onValueRemove: (InputValue.Entered<City>) -> Unit,
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
                it.selectFilterText().contains(value.value, ignoreCase = true) && !selectedValues.contains(it)
            }
            .sortedBy { it.name }
            .take(maxSearchResult)
            .toList()
    }
    var expanded by remember(suggestions, value, isFocused) {
        mutableStateOf(isFocused && suggestions.isNotEmpty() && !value.violatesRequired())
    }
    val handleSuggestionSelected: (suggestion: City) -> Unit = {
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
        SelectedCities(selectedValues) {
            onValueRemove(InputValue.Entered(it))
        }
        if (expanded)
            DropDownMenu(handleOnDismissRequested, suggestions, handleSuggestionSelected)
    }
}

@Composable
private fun SelectedCities(
    selectedValues: List<City>,
    onValueRemove: (City) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .padding(
                vertical = FwTheme.dimens.standard16,
                horizontal = FwTheme.dimens.standard12,
            )
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(FwTheme.dimens.standard8)
    ) {
        itemsIndexed(items = selectedValues) { index, item ->
            CityView(item, onClick = onValueRemove) {
                Icon(
                    modifier = Modifier.size(FwTheme.dimens.standard24),
                    painter = rememberVectorPainter(vectorResource(Res.drawable.ic_delete)),
                    tint = FwTheme.colors.blues.secondary,
                    contentDescription = null,
                )
            }
            if (index < selectedValues.lastIndex) {
                HorizontalDivider(
                    modifier = Modifier
                        .padding(
                            start = FwTheme.dimens.standard36,
                            end = FwTheme.dimens.standard24,
                        )
                        .padding(top = FwTheme.dimens.standard6)
                        .fillMaxWidth(),
                    thickness = FwTheme.dimens.standard1,
                    color = FwTheme.colors.whites.light,
                )
            }
        }
    }
}

@Composable
private fun ColumnScope.DropDownMenu(
    handleOnDismissRequested: () -> Unit,
    suggestions: List<City>,
    handleSuggestionSelected: (suggestion: City) -> Unit,
) {
    DropdownMenu(
        properties = PopupProperties(
            focusable = false,
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        ),
        modifier = Modifier
            .weight(0.5f)
            .heightIn(max = FwTheme.dimens.standard256)
            .background(FwTheme.colors.whites.secondary)
            .testTag("text_input_suggestions"),
        expanded = true,
        onDismissRequest = handleOnDismissRequested
    ) {
        suggestions.forEach {
            DropdownMenuItem(
                onClick = { handleSuggestionSelected(it) },
                text = { CityView(it, onClick = handleSuggestionSelected) },
            )
        }
    }
}

internal fun City.selectFilterText(): String =
    "${name.lowercase()}, ${country.name.lowercase()}"