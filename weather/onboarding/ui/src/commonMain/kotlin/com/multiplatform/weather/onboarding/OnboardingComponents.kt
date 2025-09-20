package com.multiplatform.weather.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import com.multiplatform.td.core.ui.overlay.TdErrorScreen
import com.multiplatform.td.core.ui.overlay.TdErrorScreenDefaultActions
import com.multiplatform.weather.core.ui.FwPrimaryButton
import com.multiplatform.weather.core.ui.FwTheme
import org.jetbrains.compose.resources.stringResource
import tdmultiplatform.weather.onboarding.ui.generated.resources.Res
import tdmultiplatform.weather.onboarding.ui.generated.resources.onboarding_ui_continue
import tdmultiplatform.weather.onboarding.ui.generated.resources.onboarding_ui_done

@Composable
internal fun OnboardingFailureView(
    uiState: UiState.Failure,
    onClick: () -> Unit,
) {
    val message = when (uiState) {
        is UiState.Failure.Res -> stringResource(uiState.stringResource)
        is UiState.Failure.Text -> uiState.message
    }
    TdErrorScreen(
        message = message,
        actions = { TdErrorScreenDefaultActions(onPrimaryActionClick = onClick) },
    )
}

@Composable
internal fun ContinueButton(
    isEnabled: Boolean = true,
    onClick: () -> Unit,
) {
    FwPrimaryButton(
        modifier = Modifier.testTag("button_continue"),
        isEnabled = isEnabled,
        text = stringResource(Res.string.onboarding_ui_continue),
        onClick = onClick,
    )
}

@Composable
internal fun DoneButton(
    isEnabled: Boolean = true,
    onClick: () -> Unit,
) {
    FwPrimaryButton(
        modifier = Modifier.testTag("button_done"),
        isEnabled = isEnabled,
        text = stringResource(Res.string.onboarding_ui_done),
        onClick = onClick,
    )
}

@Composable
internal fun OnboardingLayout(
    modifier: Modifier = Modifier,
    title: String,
    body: String,
    action: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
            .padding(horizontal = FwTheme.dimens.standard16),
        verticalArrangement = Arrangement.spacedBy(
            FwTheme.dimens.standard16,
        ),
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = title,
            style = FwTheme.typography.titlePrimary.copy(
                color = FwTheme.colors.blues.secondary,
            ),
            textAlign = TextAlign.Center,
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = body,
            style = FwTheme.typography.bodyPrimary.copy(
                color = FwTheme.colors.blues.secondary,
            ),
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.weight(.25f))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            content()
        }
        Spacer(modifier = Modifier.weight(1f))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = FwTheme.dimens.standard32),
        ) {
            action()
        }
    }
}
