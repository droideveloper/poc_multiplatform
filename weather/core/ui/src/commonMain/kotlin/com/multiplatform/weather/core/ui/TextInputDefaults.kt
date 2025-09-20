package com.multiplatform.weather.core.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Clear
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

internal val fwInputColors: TextFieldColors
    @Composable
    get() = TextFieldDefaults.colors(
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        errorContainerColor = Color.Transparent,
        unfocusedIndicatorColor = FwTheme.colors.blues.primary,
        focusedIndicatorColor = FwTheme.colors.blues.secondary,
        errorIndicatorColor = TdTheme.colors.reds.primary,
        disabledIndicatorColor = TdTheme.colors.greys.primary,
        unfocusedLabelColor = FwTheme.colors.blues.primary,
        focusedLabelColor = FwTheme.colors.blues.primary,
        errorLabelColor = Color.Red,
        disabledLabelColor = FwTheme.colors.greys.secondary,
        focusedTextColor = FwTheme.colors.blues.primary,
        unfocusedTextColor = FwTheme.colors.blues.secondary,
        focusedPlaceholderColor = FwTheme.colors.greys.primary,
        unfocusedPlaceholderColor = FwTheme.colors.greys.primary,
        disabledPlaceholderColor = FwTheme.colors.greys.primary,
    )

@Composable
internal fun FwInputLabel(
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
internal fun FwInputPlaceholder(
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
internal fun FwInputError(text: String) {
    Box(
        modifier = Modifier.padding(top = FwTheme.dimens.standard4),
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
internal fun FwInputCounter(
    modifier: Modifier = Modifier,
    length: Int,
    maxLength: Int,
) {
    Box(
        modifier = Modifier
            .padding(top = FwTheme.dimens.standard4)
            .then(modifier),
    ) {
        Text(
            text = "$length / $maxLength",
            style = TdTheme.typography.bodyTiny,
        )
    }
}

@Composable
internal fun FwInputClearButton(
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
            painter = rememberVectorPainter(Icons.Sharp.Clear),
            contentDescription = null,
            colorFilter = colorFilter,
        )
    }
}
