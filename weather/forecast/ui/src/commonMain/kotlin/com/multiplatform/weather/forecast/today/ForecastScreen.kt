package com.multiplatform.weather.forecast.today

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.multiplatform.td.core.app.composable.LocalComponentStore
import com.multiplatform.td.core.app.composable.TickEffect
import com.multiplatform.td.core.app.inject.store
import com.multiplatform.td.core.app.viewmodel.kotlinInjectViewModel
import com.multiplatform.td.core.datastore.composable.LocalDataSoreComponent
import com.multiplatform.td.core.environment.inject.EnvironmentComponent
import com.multiplatform.td.core.environment.inject.create
import com.multiplatform.td.core.log.inject.LoggerComponent
import com.multiplatform.td.core.log.inject.create
import com.multiplatform.td.core.navigation.composable.LocalNavigationComponent
import com.multiplatform.td.core.network.Url
import com.multiplatform.td.core.network.inject.NetworkComponent
import com.multiplatform.td.core.network.inject.create
import com.multiplatform.td.core.ui.KoverIgnore
import com.multiplatform.td.core.ui.effects.OnScreenStart
import com.multiplatform.td.core.ui.extensions.ignoreHorizontalPadding
import com.multiplatform.weather.core.ui.FwLoadingOverlay
import com.multiplatform.weather.core.ui.FwTheme
import com.multiplatform.weather.core.ui.selectDayBackground
import com.multiplatform.weather.forecast.ForecastErrorView
import com.multiplatform.weather.forecast.SelectedCityToolbar
import com.multiplatform.weather.forecast.WeatherDescription
import com.multiplatform.weather.forecast.WeatherDetailDescription
import com.multiplatform.weather.forecast.WeatherHourlyDescription
import com.multiplatform.weather.forecast.WeatherNextDayDescriptionItem
import com.multiplatform.weather.forecast.inject.ForecastComponent
import com.multiplatform.weather.forecast.inject.createForecastComponent
import com.multiplatform.weather.forecast.nextdays.city
import com.multiplatform.weather.forecast.nextdays.forecast
import com.multiplatform.weather.forecast.selectCurrentDateTime
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import org.jetbrains.compose.resources.stringResource
import tdmultiplatform.weather.forecast.ui.generated.resources.Res
import tdmultiplatform.weather.forecast.ui.generated.resources.forecast_ui_go_back
import kotlin.time.Duration.Companion.seconds

@KoverIgnore
@Composable
internal fun ForecastScreen() {
    val component = rememberForecastComponent()
    val viewModel = kotlinInjectViewModel(
        create = component.forecastViewModelFactory,
    )
    ForecastUi(viewModel.state, viewModel::dispatch)
}

@KoverIgnore
@Composable
internal fun rememberForecastComponent(): ForecastComponent {
    val navigationComponent = LocalNavigationComponent.current
    val dataStoreComponent = LocalDataSoreComponent.current

    val componentStore = LocalComponentStore.current

    val loggerComponent: LoggerComponent = componentStore.store {
        LoggerComponent.create(
            environmentComponent = componentStore.store {
                EnvironmentComponent.create()
            },
        )
    }
    val networkComponent: NetworkComponent = componentStore.store {
        NetworkComponent.create(
            loggerComponent = loggerComponent,
            url = Url.getOrThrow("https://api.open-meteo.com/"),
        )
    }
    return componentStore.store {
        createForecastComponent(
            networkComponent = networkComponent,
            navigationComponent = navigationComponent,
            dataStoreComponent = dataStoreComponent,
        )
    }
}

@KoverIgnore
@Composable
private fun ForecastUi(
    state: ForecastState,
    dispatch: (ForecastEvent) -> Unit,
) {
    when (val uiState = state.uiState) {
        UiState.Loading -> FwLoadingOverlay()
        UiState.Success -> ForecastSuccessView(state, dispatch)
        is UiState.Failure -> ForecastErrorView(
            uiState = uiState,
            onPrimaryClick = { dispatch(ForecastEvent.OnTryAgainClicked) },
            secondaryButtonText = stringResource(Res.string.forecast_ui_go_back),
            onSecondaryClick = { dispatch(ForecastEvent.OnBackClicked) },
        )
    }
    OnScreenStart { dispatch(ForecastEvent.OnScreenViewed) }
    TickEffect(
        onTick = { dispatch(ForecastEvent.OnTick) },
        duration = 15.seconds,
    )
}

@Composable
internal fun ForecastSuccessView(
    state: ForecastState,
    dispatch: (ForecastEvent) -> Unit,
) {
    Scaffold {
        val brush = selectDayBackground()
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .background(brush)
                .padding(it)
                .padding(horizontal = FwTheme.dimens.standard16)
                .wrapContentHeight()
                .fillMaxWidth()
                .verticalScroll(scrollState),
        ) {
            Spacer(modifier = Modifier.height(FwTheme.dimens.standard32))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = selectCurrentDateTime(state.currentLocalDateTime),
                style = FwTheme.typography.bodySecondary.copy(
                    fontWeight = FontWeight.SemiBold,
                ),
            )
            Spacer(modifier = Modifier.height(FwTheme.dimens.standard8))
            SelectedCityToolbar(
                state = state.selectedCityState,
                onPrimaryClicked = { dispatch(ForecastEvent.OnSettingsClicked) },
                onCityChangeClicked = { city -> dispatch(ForecastEvent.OnCityChanged(city)) },
            )
            Spacer(modifier = Modifier.height(FwTheme.dimens.standard32))
            WeatherDescription(
                state = state.weatherDescriptionState,
                unit = state.settings.temperatureUnit,
            )
            Spacer(modifier = Modifier.height(FwTheme.dimens.standard32))
            WeatherDetailDescription(
                modifier = Modifier.padding(horizontal = FwTheme.dimens.standard48),
                state = state.weatherDetailDescriptionState,
                temperatureUnit = state.settings.temperatureUnit,
                windUnit = state.settings.speedUnit,
            )
            Spacer(modifier = Modifier.height(FwTheme.dimens.standard16))
            WeatherHourlyDescription(
                modifier = Modifier.ignoreHorizontalPadding(FwTheme.dimens.standard16),
                state = state.weatherHourlyDescriptionState,
                unit = state.settings.temperatureUnit,
                days = state.settings.days,
                onClickNextDays = { dispatch(ForecastEvent.OnNextDaysClicked(state.city)) },
            )
            Spacer(modifier = Modifier.height(FwTheme.dimens.standard16))
            state.weatherNextDayDescriptionStates.forEach { nextDayState ->
                WeatherNextDayDescriptionItem(
                    modifier = Modifier.padding(top = FwTheme.dimens.standard12),
                    state = nextDayState,
                    unit = state.settings.temperatureUnit,
                    currentDateTime = state.currentLocalDateTime,
                )
            }
            Spacer(modifier = Modifier.height(FwTheme.dimens.standard32))
        }
    }
}

internal val forecastState = ForecastState(
    uiState = UiState.Success,
    forecast = forecast,
    selectedCities = listOf(city),
    city = city,
    currentLocalDateTime = LocalDateTime(
        date = LocalDate(2025, 7, 1),
        time = LocalTime(9, 30),
    ),
)

@Preview
@Composable
private fun ForecastSuccessViewPreview() {
    FwTheme {
        ForecastSuccessView(
            state = forecastState,
            dispatch = {},
        )
    }
}
