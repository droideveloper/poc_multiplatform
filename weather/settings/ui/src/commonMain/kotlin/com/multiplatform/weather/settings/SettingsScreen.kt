package com.multiplatform.weather.settings

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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.multiplatform.td.core.app.AppComponent
import com.multiplatform.td.core.app.composable.LocalAppComponent
import com.multiplatform.td.core.app.composable.LocalComponentStore
import com.multiplatform.td.core.app.inject.store
import com.multiplatform.td.core.app.viewmodel.kotlinInjectViewModel
import com.multiplatform.td.core.datastore.composable.LocalDataSoreComponent
import com.multiplatform.td.core.navigation.composable.LocalNavigationComponent
import com.multiplatform.td.core.ui.effects.OnScreenStart
import com.multiplatform.td.core.ui.navbar.NavBarDefaults
import com.multiplatform.td.core.ui.overlay.TdErrorScreen
import com.multiplatform.td.core.ui.overlay.TdErrorScreenDefaultActions
import com.multiplatform.weather.city.CityWidget
import com.multiplatform.weather.core.measure.Speed
import com.multiplatform.weather.core.measure.Temperature
import com.multiplatform.weather.core.ui.FwLoadingOverlay
import com.multiplatform.weather.core.ui.FwNavBar
import com.multiplatform.weather.core.ui.FwTheme
import com.multiplatform.weather.core.ui.selectDayBackground
import com.multiplatform.weather.settings.inject.SettingsComponent
import com.multiplatform.weather.settings.inject.createSettingsComponent
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import tdmultiplatform.weather.settings.ui.generated.resources.Res
import tdmultiplatform.weather.settings.ui.generated.resources.ic_decrement
import tdmultiplatform.weather.settings.ui.generated.resources.ic_increment
import tdmultiplatform.weather.settings.ui.generated.resources.settings_ui_number_of_days
import tdmultiplatform.weather.settings.ui.generated.resources.settings_ui_speed_unit
import tdmultiplatform.weather.settings.ui.generated.resources.settings_ui_temperature_unit
import tdmultiplatform.weather.settings.ui.generated.resources.settings_ui_title

@Composable
internal fun SettingsScreen() {
    val component = rememberSettingsComponent()
    val viewModel = kotlinInjectViewModel(
        create = component.settingsViewModelFactory,
    )
    SettingsUi(viewModel.state, viewModel::dispatch)
}

@Composable
private fun rememberSettingsComponent(): SettingsComponent {
    val navigationComponent = LocalNavigationComponent.current
    val dataStoreComponent = LocalDataSoreComponent.current

    val componentStore = LocalComponentStore.current

    return componentStore.store {
        createSettingsComponent(
            dataStoreComponent = dataStoreComponent,
            navigationComponent = navigationComponent,
        )
    }
}

@Composable
private fun SettingsUi(
    state: SettingsState,
    dispatch: (SettingsEvent) -> Unit,
) {
    when (val uiState = state.uiState) {
        UiState.Loading -> FwLoadingOverlay()
        is UiState.Failure -> SettingsFailureView(uiState, dispatch)
        is UiState.Success -> SettingsSuccessView(
            settings = uiState.settings.value,
            dispatch = dispatch,
        )
    }
    OnScreenStart { dispatch(SettingsEvent.OnScreenViewed) }
}

@Composable
private fun SettingsFailureView(
    uiState: UiState.Failure,
    dispatch: (SettingsEvent) -> Unit,
) {
    val message = when (uiState) {
        is UiState.Failure.Res -> stringResource(uiState.stringResource)
        is UiState.Failure.Text -> uiState.message
    }
    TdErrorScreen(
        message = message,
        actions = { TdErrorScreenDefaultActions { dispatch(SettingsEvent.OnTryAgainClicked) } },
    )
}

@Composable
private fun SettingsSuccessView(
    settings: Settings,
    dispatch: (SettingsEvent) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            FwNavBar(
                title = NavBarDefaults.Title.Default.Res(
                    stringRes = Res.string.settings_ui_title,
                ),
                navAction = NavBarDefaults.ArrowBackAction {
                    dispatch(SettingsEvent.OnBackClicked)
                },
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(selectDayBackground())
                .padding(horizontal = FwTheme.dimens.standard16)
                .padding(padding),
            verticalArrangement = Arrangement.spacedBy(
                space = FwTheme.dimens.standard16,
            ),
        ) {
            TemperatureSection(
                temperature = settings.temperatureUnit,
                onClick = { dispatch(SettingsEvent.OnTemperature.Change(it)) },
            )
            WindSpeedSection(
                speed = settings.speedUnit,
                onClick = { dispatch(SettingsEvent.OnSpeed.Change(it)) },
            )
            NumberOfDaysSection(
                numberOfDays = settings.days,
                onClickIncrement = { dispatch(SettingsEvent.OnDays.Increment) },
                onClickDecrement = { dispatch(SettingsEvent.OnDays.Decrement) },
            )
            Spacer(modifier = Modifier.height(FwTheme.dimens.standard16))
            SettingsVersion()
            Spacer(modifier = Modifier.height(FwTheme.dimens.standard32))
            CityWidget(
                allowLastSelectionRemoval = false,
                onCityRemoved = { dispatch(SettingsEvent.Operation.Remove(it)) },
                onCitySelect = { dispatch(SettingsEvent.Operation.Add(it)) },
            )
        }
    }
}

@Composable
fun TemperatureSection(
    temperature: Temperature,
    temperatures: List<Temperature> = listOf(Temperature.Celsius, Temperature.Fahrenheit),
    onClick: (Temperature) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
    ) {
        SettingLabel(text = stringResource(Res.string.settings_ui_temperature_unit))
        Spacer(modifier = Modifier.height(FwTheme.dimens.standard16))
        SingleChoiceSegmentedButtonRow(
            modifier = Modifier.fillMaxWidth(),
        ) {
            temperatures.forEachIndexed { index, unit ->
                FwSegmentedButton(
                    index = index,
                    count = temperatures.size,
                    isSelected = unit == temperature,
                    text = unit.toString(),
                ) {
                    onClick(unit)
                }
            }
        }
    }
}

@Composable
fun WindSpeedSection(
    speed: Speed,
    speeds: List<Speed> = listOf(
        Speed.KilometersPerHour,
        Speed.MetersPerSecond,
        Speed.MilesPerHour,
        Speed.Knots,
    ),
    onClick: (Speed) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .selectableGroup(),
        verticalArrangement = Arrangement.Top,
    ) {
        SettingLabel(text = stringResource(Res.string.settings_ui_speed_unit))
        Spacer(modifier = Modifier.height(FwTheme.dimens.standard16))
        SingleChoiceSegmentedButtonRow(
            modifier = Modifier.fillMaxWidth(),
        ) {
            speeds.forEachIndexed { index, unit ->
                FwSegmentedButton(
                    index = index,
                    count = speeds.size,
                    text = unit.toString(),
                    isSelected = unit == speed,
                ) {
                    onClick(unit)
                }
            }
        }
    }
}

@Composable
fun NumberOfDaysSection(
    numberOfDays: Int,
    onClickIncrement: () -> Unit,
    onClickDecrement: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SettingRowLabel(text = stringResource(Res.string.settings_ui_number_of_days))
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                modifier = Modifier
                    .size(FwTheme.dimens.standard24)
                    .background(
                        color = FwTheme.colors.whites.secondary.copy(
                            alpha = .5f,
                        ),
                        shape = RoundedCornerShape(FwTheme.dimens.standard12),
                    )
                    .padding(FwTheme.dimens.standard4),
                onClick = onClickDecrement,
                enabled = numberOfDays > 2,
            ) {
                Icon(
                    painter = rememberVectorPainter(vectorResource(Res.drawable.ic_decrement)),
                    contentDescription = null,
                )
            }
            Spacer(modifier = Modifier.width(FwTheme.dimens.standard8))
            Text(
                modifier = Modifier.padding(horizontal = FwTheme.dimens.standard4),
                text = "$numberOfDays",
                style = FwTheme.typography.titleSecondary.copy(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 36.sp,
                ),
            )
            Spacer(modifier = Modifier.width(FwTheme.dimens.standard8))
            IconButton(
                modifier = Modifier
                    .size(FwTheme.dimens.standard24)
                    .background(
                        color = FwTheme.colors.whites.secondary.copy(
                            alpha = .5f,
                        ),
                        shape = RoundedCornerShape(FwTheme.dimens.standard12),
                    )
                    .padding(FwTheme.dimens.standard4),
                onClick = onClickIncrement,
                enabled = numberOfDays < 10,
            ) {
                Icon(
                    painter = rememberVectorPainter(vectorResource(Res.drawable.ic_increment)),
                    contentDescription = null,
                )
            }
        }
    }
}

@Composable
private fun SettingLabel(
    text: String,
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = FwTheme.dimens.standard16),
        text = text,
        style = FwTheme.typography.titleSecondary.copy(
            fontWeight = FontWeight.SemiBold,
        ),
    )
}

@Composable
private fun SettingsVersion() {
    val appComponent: AppComponent = LocalAppComponent.current

    val environment = remember { appComponent.environment }

    val version = "${appComponent.version.value} - ${environment.flavorName}"
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier.padding(top = FwTheme.dimens.standard16),
            text = version,
            style = FwTheme.typography.titleSecondary.copy(
                fontWeight = FontWeight.SemiBold,
            ),
        )
    }
}

@Composable
private fun SettingRowLabel(
    text: String,
) {
    Text(
        modifier = Modifier.wrapContentWidth(),
        text = text,
        style = FwTheme.typography.titleSecondary.copy(
            fontWeight = FontWeight.SemiBold,
        ),
    )
}
