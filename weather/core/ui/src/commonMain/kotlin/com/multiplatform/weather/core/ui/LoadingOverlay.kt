package com.multiplatform.weather.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import tdmultiplatform.weather.core.ui.generated.resources.Res
import tdmultiplatform.weather.core.ui.generated.resources.core_ui_loading_text

@Composable
fun FwLoadingOverlay() {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .background(selectDayBackground())
                .padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Row (
                modifier = Modifier.wrapContentWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ){
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(FwTheme.dimens.standard8)
                        .size(FwTheme.dimens.standard32),
                    color = FwTheme.colors.blues.primary,
                    strokeWidth = FwTheme.dimens.standard4,
                )
                Spacer(modifier = Modifier.width(FwTheme.dimens.standard4))
                Text(
                    modifier = Modifier.padding(start = FwTheme.dimens.standard4),
                    text = stringResource(Res.string.core_ui_loading_text),
                    style = FwTheme.typography.titleSecondary.copy(
                        fontWeight = FontWeight.SemiBold,
                    )
                )
            }
        }
    }
}

@Preview
@Composable
private fun FmLoadingOverlayPreview() {
    FwTheme { FwLoadingOverlay() }
}