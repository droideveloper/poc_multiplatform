package com.multiplatform.weather.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import com.multiplatform.td.core.ui.input.InputError
import com.multiplatform.td.core.ui.input.InputValue
import com.multiplatform.td.core.ui.input.TextError
import com.multiplatform.td.core.ui.input.TextValue
import com.multiplatform.td.core.ui.input.isError
import com.multiplatform.td.core.ui.input.selectErrorMessage
import com.multiplatform.td.core.ui.input.violatesRequired
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FwTextInput(
    value: TextValue,
    onValueChange: (InputValue.Entered<String>) -> Unit,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.None,
    label: String? = null,
    enabled: Boolean = true,
    placeholder: String? = null,
    maxLength: Int? = null,
    showMaxLengthCounter: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    error: TextError = InputError.None,
    textFieldColors: TextFieldColors = fwInputColors,
    textStyle: TextStyle = FwTheme.typography.bodyPrimary,
    singleLine: Boolean = true,
    imeAction: ImeAction = ImeAction.Default,
    onDoneClicked: () -> Unit = {},
    onFocusChange: ((isFocused: Boolean) -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    var isFocused by remember { mutableStateOf(false) }

    val showClearButton = remember(value, isFocused) {
        value.violatesRequired().not() && isFocused
    }
    val interactionSource = remember { MutableInteractionSource() }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
    ) {
        label?.let {
            FwInputLabel(
                text = it,
                style = FwTheme.typography.bodySecondary.copy(
                    color = FwTheme.colors.blues.secondary,
                    fontWeight = FontWeight.SemiBold,
                ),
            )
        }

        BasicTextField(
            value = value.value,
            onValueChange = { text ->
                val textToEmit = maxLength
                    ?.let { text.take(it) }
                    ?: text
                onValueChange(InputValue.Entered(textToEmit))
            },
            singleLine = singleLine,
            modifier = Modifier
                .indicatorLine(true, error.isError(), interactionSource, textFieldColors)
                .fillMaxWidth()
                .onFocusChanged {
                    onFocusChange?.invoke(it.isFocused)
                    isFocused = it.isFocused
                }
                .testTag("text_input"),
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = capitalization,
                keyboardType = keyboardType,
                imeAction = imeAction,
            ),
            keyboardActions = KeyboardActions(onDone = {
                defaultKeyboardAction(ImeAction.Done)
                onDoneClicked()
            }),
            visualTransformation = visualTransformation,
            textStyle = textStyle,
        ) { innerTextField ->
            TextFieldDefaults.DecorationBox(
                value = value.value,
                visualTransformation = visualTransformation,
                innerTextField = innerTextField,
                placeholder = placeholder?.let {
                    {
                        FwInputPlaceholder(
                            text = it,
                            style = textStyle.copy(
                                color = FwTheme.colors.greys.primary,
                            ),
                        )
                    }
                },
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon ?: if (showClearButton) {
                    {
                        FwInputClearButton(
                            colorFilter = ColorFilter.tint(
                                color = FwTheme.colors.blues.primary,
                            ),
                            onClick = { onValueChange(InputValue.Entered("")) },
                        )
                    }
                } else {
                    null
                },
                singleLine = singleLine,
                colors = textFieldColors,
                isError = error.isError(),
                enabled = enabled,
                interactionSource = interactionSource,
                contentPadding = PaddingValues(vertical = FwTheme.dimens.standard8),
            )
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            if (error.isError()) FwInputError(text = error.selectErrorMessage())
            Spacer(modifier = Modifier.weight(1f))
            if (maxLength != null && showMaxLengthCounter) {
                FwInputCounter(length = value.value.length, maxLength = maxLength)
            }
        }
    }
}

@Preview
@Composable
private fun FwTextInputPreview() {
    FwTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
                .padding(FwTheme.dimens.standard8),
        ) {
            FwTextInput(
                label = "Enter Sth",
                value = InputValue.Entered("Hello World"),
                onValueChange = { },
            )
        }
    }
}
