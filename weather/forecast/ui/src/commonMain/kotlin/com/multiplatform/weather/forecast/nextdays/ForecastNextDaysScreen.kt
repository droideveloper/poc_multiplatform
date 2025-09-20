package com.multiplatform.weather.forecast.nextdays

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.multiplatform.td.core.app.composable.TickEffect
import com.multiplatform.td.core.app.viewmodel.kotlinInjectViewModel
import com.multiplatform.td.core.ui.card.TdCard
import com.multiplatform.td.core.ui.effects.OnScreenStart
import com.multiplatform.td.core.ui.extensions.ignoreHorizontalPadding
import com.multiplatform.td.core.ui.navbar.NavBarDefaults
import com.multiplatform.weather.city.City
import com.multiplatform.weather.city.Country
import com.multiplatform.weather.city.CountryCode
import com.multiplatform.weather.city.Location
import com.multiplatform.weather.core.measure.Temperature
import com.multiplatform.weather.core.measure.TemperatureAmount
import com.multiplatform.weather.core.ui.FwImage
import com.multiplatform.weather.core.ui.FwLoadingOverlay
import com.multiplatform.weather.core.ui.FwNavBar
import com.multiplatform.weather.core.ui.FwTheme
import com.multiplatform.weather.core.ui.selectDayBackground
import com.multiplatform.weather.forecast.WeatherCode
import com.multiplatform.weather.forecast.WeatherHourlyDescriptionItem
import com.multiplatform.weather.forecast.selectLocalDate
import com.multiplatform.weather.forecast.selectTemperature
import com.multiplatform.weather.forecast.selectWeatherDescription
import com.multiplatform.weather.forecast.today.UiState
import com.multiplatform.weather.forecast.today.WeatherDescriptionState
import com.multiplatform.weather.forecast.today.WeatherHourlyDescriptionState
import com.multiplatform.weather.forecast.today.WeatherNextDayDescriptionState
import com.multiplatform.weather.forecast.today.rememberForecastComponent
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import tdmultiplatform.weather.forecast.ui.generated.resources.Res
import tdmultiplatform.weather.forecast.ui.generated.resources.forecast_ui_number_of_days
import tdmultiplatform.weather.forecast.ui.generated.resources.forecast_ui_today
import kotlin.time.Duration.Companion.seconds

@Composable
internal fun ForecastNextDaysScreen(cityId: Long) {
    val component = rememberForecastComponent()
    val viewModel = kotlinInjectViewModel(
        param = cityId,
        create = component.forecastNextDaysViewModelFactory,
    )
    ForecastNextDaysUi(viewModel.state, viewModel::dispatch)
}

@Composable
internal fun ForecastNextDaysUi(
    state: ForecastNextDaysState,
    dispatch: (ForecastNextDaysEvent) -> Unit,
) {
    when (val uiState = state.uiState) {
        UiState.Loading -> FwLoadingOverlay()
        UiState.Success -> ForecastNextDaysSuccessView(state, dispatch)
        else -> Unit // TODO implement this
    }
    OnScreenStart { dispatch(ForecastNextDaysEvent.OnScreenViewed) }
    TickEffect(
        onTick = { dispatch(ForecastNextDaysEvent.OnTick) },
        duration = 15.seconds,
    )
}

@Composable
internal fun ForecastNextDaysSuccessView(
    state: ForecastNextDaysState,
    dispatch: (ForecastNextDaysEvent) -> Unit,
) {
    Scaffold(
        topBar = {
            FwNavBar(
                title = NavBarDefaults.Title.Default.Text(
                    text = stringResource(Res.string.forecast_ui_number_of_days, state.settings.days),
                ),
                navAction = NavBarDefaults.ArrowBackAction {
                    dispatch(ForecastNextDaysEvent.OnBackClicked)
                },
            )
        },
    ) {
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
            Spacer(modifier = Modifier.height(FwTheme.dimens.standard16))
            TodayHourlyDescription(
                modifier = Modifier.ignoreHorizontalPadding(FwTheme.dimens.standard16),
                state = state.weatherHourlyDescriptionState,
                unit = state.settings.temperatureUnit,
            )
            Spacer(modifier = Modifier.height(FwTheme.dimens.standard32))
            TodayWeatherDescription(
                state = state.weatherDescriptionState,
                city = state.city,
                unit = state.settings.temperatureUnit,
            )
            Spacer(modifier = Modifier.height(FwTheme.dimens.standard32))
            state.weatherNextDayDescriptionStates.forEach { nthDayState ->
                NthDayWeatherDescription(
                    state = nthDayState,
                    unit = state.settings.temperatureUnit,
                )
            }
        }
    }
}

@Composable
internal fun TodayHourlyDescription(
    modifier: Modifier = Modifier,
    state: WeatherHourlyDescriptionState,
    unit: Temperature,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = FwTheme.dimens.standard16)
            .then(modifier),
    ) {
        val scrollState = rememberLazyListState()
        LazyRow(
            modifier = Modifier
                .ignoreHorizontalPadding(FwTheme.dimens.standard16)
                .fillMaxWidth(),
            state = scrollState,
            horizontalArrangement = Arrangement.spacedBy(
                space = FwTheme.dimens.standard8,
            ),
        ) {
            items(
                items = state.hourlyWeather,
                key = { it.time.toMillisecondOfDay() },
            ) { item ->
                WeatherHourlyDescriptionItem(
                    currentTime = state.time,
                    weather = item,
                    unit = unit,
                )
            }
        }
        LaunchedEffect(state.selectedPosition) {
            scrollState.animateScrollToItem(
                index = state.selectedPosition,
            )
        }
    }
}

@Composable
internal fun TodayWeatherDescription(
    modifier: Modifier = Modifier,
    state: WeatherDescriptionState,
    city: City,
    unit: Temperature,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = FwTheme.colors.blues.light.copy(
                    alpha = 0.35f,
                ),
                shape = RoundedCornerShape(FwTheme.dimens.standard8),
            )
            .padding(horizontal = FwTheme.dimens.standard16)
            .padding(vertical = FwTheme.dimens.standard32)
            .then(modifier),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val (descriptionRes, weatherIconRes) = selectWeatherDescription(state.weatherCode)
        Column {
            FwImage(
                modifier = Modifier.width(FwTheme.dimens.standard96),
                resource = vectorResource(weatherIconRes),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.height(FwTheme.dimens.standard8))
            Text(
                text = stringResource(Res.string.forecast_ui_today),
                style = FwTheme.typography.bodyPrimary.copy(
                    fontWeight = FontWeight.Bold,
                    color = FwTheme.colors.greys.secondary,
                ),
            )
            Spacer(modifier = Modifier.height(FwTheme.dimens.standard4))
            Text(
                text = "${city.name}, ${city.country.name}",
                style = FwTheme.typography.bodySecondary,
            )
        }
        Spacer(modifier = Modifier.width(FwTheme.dimens.standard16))
        Column {
            Text(
                text = selectTemperature(state.temperature, unit),
                style = FwTheme.typography.titlePrimary.copy(
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Bold,
                ),
            )
            Spacer(modifier = Modifier.height(FwTheme.dimens.standard6))
            Text(
                modifier = Modifier.sizeIn(maxWidth = FwTheme.dimens.standard148),
                text = stringResource(descriptionRes),
                style = FwTheme.typography.bodySecondary.copy(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                ),
            )
        }
    }
}

@Composable
internal fun NthDayWeatherDescription(
    modifier: Modifier = Modifier,
    state: WeatherNextDayDescriptionState,
    unit: Temperature,
) {
    TdCard(
        modifier = Modifier
            .padding(FwTheme.dimens.standard4)
            .then(modifier),
        shape = RoundedCornerShape(FwTheme.dimens.standard4),
        containerColor = FwTheme.colors.whites.secondary,
    ) {
        Row(
            modifier = Modifier
                .padding(
                    horizontal = FwTheme.dimens.standard16,
                    vertical = FwTheme.dimens.standard12,
                )
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val (weatherDescriptionRes, iconRes) = selectWeatherDescription(state.weatherCode)
            Icon(
                modifier = Modifier
                    .size(FwTheme.dimens.standard36)
                    .background(
                        color = FwTheme.colors.whites.light.copy(
                            alpha = 0.5f,
                        ),
                        shape = RoundedCornerShape(FwTheme.dimens.standard16),
                    )
                    .padding(FwTheme.dimens.standard6),
                painter = rememberVectorPainter(vectorResource(iconRes)),
                contentDescription = stringResource(weatherDescriptionRes),
                tint = Color.Unspecified,
            )
            Spacer(modifier = Modifier.width(FwTheme.dimens.standard8))
            Text(
                text = selectLocalDate(state.date),
                style = FwTheme.typography.bodySecondary.copy(
                    color = FwTheme.colors.greys.secondary,
                    fontWeight = FontWeight.Bold,
                ),
            )
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(weatherDescriptionRes),
                textAlign = TextAlign.Center,
                style = FwTheme.typography.spotPrimary.copy(
                    color = FwTheme.colors.greys.secondary,
                ),
            )
            Text(
                text = selectTemperature(state.temperature, unit),
                style = FwTheme.typography.titleSecondary.copy(
                    color = FwTheme.colors.greys.secondary,
                    fontWeight = FontWeight.Bold,
                ),
            )
        }
    }
}

@Preview
@Composable
private fun TodayWeatherDescriptionPreview() {
    FwTheme {
        Column(
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxWidth()
                .padding(
                    horizontal = FwTheme.dimens.standard16,
                    vertical = FwTheme.dimens.standard8,
                ),
        ) {
            TodayWeatherDescription(
                state = WeatherDescriptionState(
                    temperature = TemperatureAmount(
                        amount = 32.0,
                        unit = Temperature.Celsius,
                    ),
                    weatherCode = WeatherCode.getOrThrow(92),
                ),
                city = City(
                    id = 0,
                    name = "Istanbul",
                    displayName = "Istanbul",
                    country = Country(
                        name = "Turkey",
                        code = CountryCode.getOrThrow("TR"),
                    ),
                    location = Location(
                        latitude = 0.0,
                        longitude = 0.0,
                    ),
                ),
                unit = Temperature.Celsius,
            )
        }
    }
}

@Preview
@Composable
private fun NthDayWeatherDescriptionPreview() {
    FwTheme {
        Column(
            modifier = Modifier
                .background(brush = selectDayBackground())
                .fillMaxWidth()
                .padding(
                    horizontal = FwTheme.dimens.standard16,
                    vertical = FwTheme.dimens.standard8,
                ),
        ) {
            NthDayWeatherDescription(
                state = WeatherNextDayDescriptionState(
                    temperature = TemperatureAmount(
                        amount = 32.0,
                        unit = Temperature.Celsius,
                    ),
                    weatherCode = WeatherCode.getOrThrow(92),
                    date = LocalDate.parse("2025-07-29"),
                ),
                unit = Temperature.Celsius,
            )
        }
    }
}
