package com.multiplatform.todo.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.multiplatform.td.core.app.composable.LocalAppComponent
import com.multiplatform.td.core.app.composable.LocalComponentStore
import com.multiplatform.td.core.app.inject.store
import com.multiplatform.td.core.app.viewmodel.kotlinInjectViewModel
import com.multiplatform.td.core.datastore.composable.LocalDataSoreComponent
import com.multiplatform.td.core.environment.AppVersion
import com.multiplatform.td.core.ui.TdTheme
import com.multiplatform.td.core.ui.controls.TdToggle
import com.multiplatform.td.core.ui.effects.OnScreenStart
import com.multiplatform.todo.core.ui.TdNavBar
import com.multiplatform.todo.settings.inject.SettingsComponent
import com.multiplatform.todo.settings.inject.createSettingsComponent
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import tdmultiplatform.todo.settings.ui.generated.resources.Res
import tdmultiplatform.todo.settings.ui.generated.resources.ic_decrement
import tdmultiplatform.todo.settings.ui.generated.resources.ic_increment
import tdmultiplatform.todo.settings.ui.generated.resources.settings_notification_title
import tdmultiplatform.todo.settings.ui.generated.resources.settings_notify_before_minutes
import tdmultiplatform.todo.settings.ui.generated.resources.settings_notify_before_title
import tdmultiplatform.todo.settings.ui.generated.resources.settings_title
import kotlin.time.Duration
import kotlin.time.DurationUnit

@Composable
fun SettingsScreen() {
    val component = rememberSettingsComponent()
    val viewModel = kotlinInjectViewModel(
        create = component.settingsViewModelFactory,
    )
    SettingsUi(viewModel.state, viewModel::dispatch)
}

@Composable
private fun rememberSettingsComponent(): SettingsComponent {
    val dataStoreComponent = LocalDataSoreComponent.current

    val componentStore = LocalComponentStore.current

    return componentStore.store {
        createSettingsComponent(
            dataStoreComponent = dataStoreComponent,
        )
    }
}

@Composable
private fun SettingsUi(
    state: SettingsState,
    dispatch: (SettingsEvent) -> Unit,
) {
    when (val uiState = state.uiState) {
        UiState.Loading -> Unit // TODO replace it later
        is UiState.Failure -> Unit // TODO replace it later
        UiState.Success -> SettingsSuccessView(state, dispatch)
    }
    OnScreenStart { dispatch(SettingsEvent.OnScreenViewed) }
}

@Composable
private fun SettingsSuccessView(
    state: SettingsState,
    dispatch: (SettingsEvent) -> Unit,
) {
    Scaffold(
        topBar = {
            TdNavBar(
                title = stringResource(Res.string.settings_title),
                secondaryTitle = selectSecondaryTitle(),
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = TdTheme.dimens.standard16)
                .background(color = TdTheme.colors.whites.primary),
        ) {
            Spacer(modifier = Modifier.height(TdTheme.dimens.standard36))
            SettingsNotificationSection(state.settings.isNotificationEnabled, dispatch)
            Spacer(modifier = Modifier.height(TdTheme.dimens.standard24))
            SettingsNotifyBeforeSection(
                isEnabled = state.settings.isNotificationEnabled,
                duration = state.settings.notifyBefore,
                onClickDecrement = {
                    dispatch(SettingsEvent.OnChange.Duration.Decrement)
                },
                onClickIncrement = {
                    dispatch(SettingsEvent.OnChange.Duration.Increment)
                },
            )
            Spacer(modifier = Modifier.weight(1f))
            SettingsVersionFooter(state.version)
            Spacer(modifier = Modifier.height(TdTheme.dimens.standard36))
        }
    }
}

@Composable
private fun SettingsNotificationSection(
    isChecked: Boolean,
    dispatch: (SettingsEvent) -> Unit,
) {
    SettingsSectionLayout(
        title = stringResource(Res.string.settings_notification_title),
    ) {
        TdToggle(
            checked = isChecked,
            stateColorProvider = { selectCheckStateColor(it) },
            onCheckedChange = { dispatch(SettingsEvent.OnChange.Notification(it)) },
        )
    }
}

@Composable
private fun SettingsNotifyBeforeSection(
    isEnabled: Boolean,
    duration: Duration,
    onClickIncrement: () -> Unit,
    onClickDecrement: () -> Unit,
) {
    SettingsSectionLayout(
        title = stringResource(Res.string.settings_notify_before_title),
    ) {
        val minutes = remember(duration) { duration.toInt(DurationUnit.MINUTES) }
        Row(
            modifier = Modifier.wrapContentSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(
                modifier = Modifier
                    .size(TdTheme.dimens.standard24)
                    .background(
                        color = selectButtonColor(isEnabled),
                        shape = RoundedCornerShape(TdTheme.dimens.standard12),
                    )
                    .padding(TdTheme.dimens.standard4),
                onClick = onClickDecrement,
                enabled = isEnabled && minutes > 0,
            ) {
                Icon(
                    painter = rememberVectorPainter(vectorResource(Res.drawable.ic_decrement)),
                    contentDescription = null,
                    tint = TdTheme.colors.whites.primary,
                )
            }
            Spacer(modifier = Modifier.width(TdTheme.dimens.standard8))
            Text(
                modifier = Modifier.padding(horizontal = TdTheme.dimens.standard4),
                text = stringResource(Res.string.settings_notify_before_minutes, minutes),
                style = TextStyle.Default.copy(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 22.sp,
                    color = selectTitleColor(isEnabled),
                ),
            )
            Spacer(modifier = Modifier.width(TdTheme.dimens.standard8))
            IconButton(
                modifier = Modifier
                    .size(TdTheme.dimens.standard24)
                    .background(
                        color = selectButtonColor(isEnabled),
                        shape = RoundedCornerShape(TdTheme.dimens.standard12),
                    )
                    .padding(TdTheme.dimens.standard4),
                onClick = onClickIncrement,
                enabled = isEnabled && minutes < 120,
            ) {
                Icon(
                    painter = rememberVectorPainter(vectorResource(Res.drawable.ic_increment)),
                    contentDescription = null,
                    tint = TdTheme.colors.whites.primary,
                )
            }
        }
    }
}

@Composable
private fun SettingsSectionLayout(
    modifier: Modifier = Modifier,
    title: String,
    content: @Composable () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            style = TextStyle.Default.copy(
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = TdTheme.colors.blacks.secondary,
            ),
        )
        Spacer(modifier = Modifier.weight(1f))
        content()
    }
}

@Composable
private fun SettingsVersionFooter(
    version: AppVersion,
) {
    Row(
        modifier = Modifier
            .padding(bottom = TdTheme.dimens.standard36)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        val appComponent = LocalAppComponent.current
        val env = remember { appComponent.environment }

        Text(
            text = "v${version.value} - ${env.flavorName}",
            style = TextStyle.Default.copy(
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = TdTheme.colors.blacks.secondary,
            ),
        )
    }
}

@Composable
internal fun selectCheckStateColor(checked: Boolean) = when {
    checked -> TdTheme.colors.deepOranges.primary
    else -> TdTheme.colors.greys.primary
}
