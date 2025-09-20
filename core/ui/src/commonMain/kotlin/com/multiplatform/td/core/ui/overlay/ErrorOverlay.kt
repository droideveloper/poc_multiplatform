package com.multiplatform.td.core.ui.overlay

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.multiplatform.td.core.ui.TdTheme
import com.multiplatform.td.core.ui.button.TdPrimaryButton
import com.multiplatform.td.core.ui.button.TdSecondaryButton
import com.multiplatform.td.core.ui.image.TdImage
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import tdmultiplatform.core.ui.generated.resources.Res
import tdmultiplatform.core.ui.generated.resources.core_ui_error_primary_title
import tdmultiplatform.core.ui.generated.resources.core_ui_error_secondary_title
import tdmultiplatform.core.ui.generated.resources.core_ui_error_title

@Composable
fun TdErrorScreen(
    title: String = stringResource(Res.string.core_ui_error_title),
    message: String,
    image: @Composable () -> Unit = { TdErrorScreenDefaultImage() },
    actions: @Composable () -> Unit = { },
) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(TdTheme.dimens.standard12),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Column(
                modifier = Modifier
                    .padding(bottom = TdTheme.dimens.standard48)
                    .weight(1F)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Box(contentAlignment = Alignment.Center) {
                    image()
                }
                Spacer(modifier = Modifier.height(TdTheme.dimens.standard24))
                ErrorTitle(title)
                Spacer(modifier = Modifier.height(TdTheme.dimens.standard24))
                ErrorMessage(message)
            }
            actions()
        }
    }
}

@Composable
fun TdErrorScreenDefaultImage() {
    TdImage(
        resource = Icons.Default.Warning,
        modifier = Modifier.sizeIn(
            minWidth = TdTheme.dimens.standard96,
            minHeight = TdTheme.dimens.standard96,
        ),
    )
}

@Composable
fun TdErrorScreenDefaultActions(
    primaryActionText: String = stringResource(Res.string.core_ui_error_primary_title),
    secondaryActionText: String = stringResource(Res.string.core_ui_error_secondary_title),
    onPrimaryActionClick: () -> Unit,
    onSecondaryActionClick: () -> Unit,
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Box(modifier = Modifier.weight(1f)) {
            TdSecondaryButton(text = secondaryActionText, onClick = onSecondaryActionClick)
        }
        Spacer(modifier = Modifier.width(TdTheme.dimens.standard12))
        Box(modifier = Modifier.weight(1f)) {
            TdPrimaryButton(text = primaryActionText, onClick = onPrimaryActionClick)
        }
    }
}

@Composable
fun TdErrorScreenDefaultActions(
    primaryActionText: String = stringResource(Res.string.core_ui_error_primary_title),
    onPrimaryActionClick: () -> Unit,
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Box(modifier = Modifier.weight(1f)) {
            TdPrimaryButton(text = primaryActionText, onClick = onPrimaryActionClick)
        }
    }
}

@Composable
private fun ErrorTitle(
    title: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = title,
        style = TdTheme.typography.titleLarge,
        textAlign = TextAlign.Center,
        modifier = modifier,
    )
}

@Composable
private fun ErrorMessage(message: String) {
    Text(
        text = message,
        style = TdTheme.typography.bodyMedium,
        textAlign = TextAlign.Center,
    )
}

@Preview
@Composable
private fun Default() = TdTheme {
    TdErrorScreen(
        title = "Title",
        message = "Message",
    )
}

@Preview
@Composable
private fun DefaultWithAction() = TdTheme {
    TdErrorScreen(
        title = "Title",
        message = "Message",
        actions = {
            TdErrorScreenDefaultActions(
                primaryActionText = "Try Again",
                onPrimaryActionClick = {},
                secondaryActionText = "Back Home",
                onSecondaryActionClick = {},
            )
        },
    )
}
