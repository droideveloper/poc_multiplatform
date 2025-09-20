package com.multiplatform.td.core.ui.input

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import com.multiplatform.td.core.ui.TdTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import tdmultiplatform.core.ui.generated.resources.Res
import tdmultiplatform.core.ui.generated.resources.core_ui_select_option

@Composable
fun TdSelectInput(
    value: TextValue,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String = stringResource(Res.string.core_ui_select_option),
    error: TextError = InputError.None,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .then(modifier),
    ) {
        label?.let { TdInputLabel(text = it, style = TdTheme.typography.titleTinySemi) }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = TdTheme.dimens.standard8),
        ) {
            value
                .value
                .takeUnless(String::isEmpty)
                ?.let { Text(it, style = TdTheme.typography.titleSmall) }
                ?: TdInputPlaceholder(
                    text = placeholder,
                    style = TdTheme.typography.titleSmall.copy(
                        color = TdTheme.colors.greys.secondary,
                    ),
                )
            Icon(
                painter = rememberVectorPainter(Icons.Filled.ArrowDropDown),
                contentDescription = null,
                tint = TdTheme.colors.greys.secondary,
            )
        }
        HorizontalDivider(color = TdTheme.colors.whites.light)
        if (error.isError()) TdInputError(text = error.selectErrorMessage())
    }
}

@Preview()
@Composable
private fun NoValue() = TdTheme {
    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp),
    ) {
        TdSelectInput(
            value = InputValue.Default(""),
            label = "Label",
            onClick = {},
        )
    }
}

@Preview()
@Composable
private fun Error() = TdTheme {
    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp),
    ) {
        TdSelectInput(
            value = InputValue.Default(""),
            label = "Label",
            onClick = {},
            error = InputError.Unspecified,
        )
    }
}

@Preview()
@Composable
private fun ValueSelected() = TdTheme {
    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp),
    ) {
        TdSelectInput(
            value = InputValue.Default("Option selected"),
            label = "Label",
            onClick = {},
        )
    }
}
