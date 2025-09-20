package com.multiplatform.td.core.ui.input

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import com.multiplatform.td.core.ui.TdTheme

internal val tdInputColors: TextFieldColors
    @Composable
    get() = TextFieldDefaults.colors(
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        errorContainerColor = Color.Transparent,
        unfocusedIndicatorColor = TdTheme.colors.blues.primary,
        focusedIndicatorColor = TdTheme.colors.oranges.primary,
        errorIndicatorColor = TdTheme.colors.reds.primary,
        disabledIndicatorColor = TdTheme.colors.greys.primary,
        unfocusedLabelColor = TdTheme.colors.greys.secondary,
        focusedLabelColor = TdTheme.colors.greens.primary,
        errorLabelColor = TdTheme.colors.reds.primary,
        disabledLabelColor = TdTheme.colors.greys.primary,
        focusedTextColor = TdTheme.colors.blacks.primary,
        unfocusedTextColor = TdTheme.colors.blacks.primary,
        disabledTextColor = TdTheme.colors.greys.primary,
        focusedPlaceholderColor = TdTheme.colors.greys.primary,
        unfocusedPlaceholderColor = TdTheme.colors.greys.primary,
        disabledPlaceholderColor = TdTheme.colors.greys.primary,
    )

internal val tdInputColorsFilled: TextFieldColors
    @Composable
    get() = TextFieldDefaults.colors(
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        errorContainerColor = Color.Transparent,
        unfocusedIndicatorColor = TdTheme.colors.whites.light,
        focusedIndicatorColor = TdTheme.colors.whites.light,
        focusedPlaceholderColor = TdTheme.colors.greys.primary,
        unfocusedPlaceholderColor = TdTheme.colors.greys.primary,
        unfocusedLabelColor = TdTheme.colors.greys.secondary,
        focusedLabelColor = TdTheme.colors.greys.secondary,
        errorLabelColor = TdTheme.colors.reds.primary,
        disabledLabelColor = TdTheme.colors.greys.primary,
        focusedTextColor = TdTheme.colors.blacks.primary,
        unfocusedTextColor = TdTheme.colors.blacks.primary,
    )

@Composable
internal fun TdInputLabel(
    text: String,
    style: TextStyle = LocalTextStyle.current,
) {
    Text(
        text = text,
        modifier = Modifier.testTag("text_input_label"),
        style = style,
    )
}

@Composable
internal fun TdInputPlaceholder(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
) {
    Text(
        text = text,
        modifier = modifier.testTag("text_input_placeholder"),
        style = style,
    )
}

@Composable
internal fun TdInputError(text: String) {
    Box(
        modifier = Modifier.padding(top = TdTheme.dimens.standard4),
    ) {
        Text(
            text = text,
            style = TdTheme.typography.titleTiny,
            color = TdTheme.colors.reds.primary,
            modifier = Modifier.testTag("text_input_error"),
        )
    }
}

@Composable
internal fun TdInputCounter(
    modifier: Modifier = Modifier,
    length: Int,
    maxLength: Int,
) {
    Box(
        modifier = Modifier
            .padding(top = TdTheme.dimens.standard4)
            .then(modifier),
    ) {
        Text(
            text = "$length / $maxLength",
            style = TdTheme.typography.bodyTiny,
        )
    }
}

@Composable
internal fun TdInputClearButton(
    colorFilter: ColorFilter? = null,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.testTag("input_clear_button"),
    ) {
        Image(
            modifier = modifier,
            painter = rememberVectorPainter(Icons.Filled.Clear),
            contentDescription = null,
            colorFilter = colorFilter,
        )
    }
}

@Composable
internal fun TdInputArrow(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.testTag("input_arrow_down_button"),
    ) {
        Image(
            painter = rememberVectorPainter(Icons.Filled.ArrowDropDown),
            contentDescription = null,
            modifier = Modifier.then(modifier),
        )
    }
}
