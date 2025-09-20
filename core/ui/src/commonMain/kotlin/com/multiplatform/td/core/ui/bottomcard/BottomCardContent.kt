package com.multiplatform.td.core.ui.bottomcard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.multiplatform.td.core.ui.TdTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TdBottomCardContentLayout(
    icon: @Composable ColumnScope.() -> Unit = {},
    title: @Composable ColumnScope.() -> Unit = {},
    message: @Composable ColumnScope.() -> Unit = {},
    modifier: Modifier = Modifier,
    buttonsBuilder: BottomCardButtonsScope.() -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .background(TdTheme.colors.whites.primary),
    ) {
        icon()
        title()
        message()
        BottomCardButtonsScopeImpl().also(buttonsBuilder).Content()
    }
}

@Composable
fun TdBottomCardContent(
    icon: @Composable ColumnScope.() -> Unit,
    title: String,
    message: CharSequence,
    modifier: Modifier = Modifier,
    buttonsBuilder: BottomCardButtonsScope.() -> Unit = {},
) {
    TdBottomCardContentLayout(
        icon = icon,
        title = {
            Spacer(modifier = Modifier.padding(top = TdTheme.dimens.standard16))
            Text(
                text = title,
                style = TdTheme.typography.titleBig,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.padding(top = TdTheme.dimens.standard16))
        },
        message = {
            Text(
                text = (message as? AnnotatedString) ?: AnnotatedString(message.toString()),
                style = TdTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.padding(top = TdTheme.dimens.standard12))
        },
        modifier = modifier,
        buttonsBuilder = buttonsBuilder,
    )
}

@Composable
fun TdBottomCardContent(
    icon: ImageVector,
    title: String,
    message: CharSequence,
    modifier: Modifier = Modifier,
    buttonsBuilder: BottomCardButtonsScope.() -> Unit = {},
) {
    TdBottomCardContent(
        icon = { BottomCardIcon(icon = icon) },
        title = title,
        message = message,
        modifier = modifier,
        buttonsBuilder = buttonsBuilder,
    )
}

@Composable
fun TdBottomCardContent(
    title: String,
    message: String,
    content: @Composable () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(TdTheme.colors.whites.primary),
    ) {
        Text(
            text = title,
            style = TdTheme.typography.titleBig,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(TdTheme.dimens.standard12))
        Text(
            text = message,
            style = TdTheme.typography.bodyMedium,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth(),
        )
        content()
    }
}

@Preview
@Composable
private fun PreviewWithIcon() = TdTheme {
    TdBottomCardContent(
        icon = Icons.Default.AccountBox,
        title = "Title goes here",
        message = "Body goes here",
    )
}

@Preview
@Composable
private fun PreviewWithAnnotatedMessage() = TdTheme {
    TdBottomCardContent(
        icon = Icons.Default.AccountBox,
        title = "Title goes here",
        message = buildAnnotatedString {
            append("Body")
            append(' ')
            pushStyle(TextStyle(fontWeight = FontWeight.Bold).toSpanStyle())
            append("goes")
            pop()
            append(' ')
            append("here")
        },
    )
}

@Preview
@Composable
private fun PreviewWithNoTitle() = TdTheme {
    TdBottomCardContent(
        icon = Icons.Default.AccountBox,
        title = "Title goes here",
        message = "Message goes here",
    )
}

@Preview
@Composable
private fun PreviewOnlyText() = TdTheme {
    TdBottomCardContent(
        title = "Title goes here",
        message = "Body goes here",
        content = {},
    )
}
