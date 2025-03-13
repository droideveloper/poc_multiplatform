package com.multiplatform.td.core.ui.overlay

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.multiplatform.td.core.ui.TdTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import tdmultiplatform.core.ui.generated.resources.Res
import tdmultiplatform.core.ui.generated.resources.core_ui_loading_text

@Composable
fun TdLoadingOverlay() {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(TdTheme.dimens.standard8)
                    .size(TdTheme.dimens.standard64),
                color = TdTheme.colors.oranges.primary,
                strokeWidth = TdTheme.dimens.standard2,
            )
            Spacer(modifier = Modifier.height(TdTheme.dimens.standard24))
            Text(
                text = stringResource(Res.string.core_ui_loading_text),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = TdTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                )
            )
        }
    }
}

@Preview
@Composable
private fun Preview() = TdTheme {
    TdLoadingOverlay()
}


