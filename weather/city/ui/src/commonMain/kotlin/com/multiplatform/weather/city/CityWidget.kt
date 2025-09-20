package com.multiplatform.weather.city

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.multiiplatform.td.core.database.composable.LocalDatabaseComponent
import com.multiplatform.td.core.app.composable.LocalComponentStore
import com.multiplatform.td.core.app.inject.store
import com.multiplatform.td.core.app.viewmodel.kotlinInjectViewModel
import com.multiplatform.td.core.datastore.composable.LocalDataSoreComponent
import com.multiplatform.td.core.ui.button.TdTextLinkBlue
import com.multiplatform.td.core.ui.effects.OnScreenStart
import com.multiplatform.weather.city.inject.CityComponent
import com.multiplatform.weather.city.inject.createCityComponent
import com.multiplatform.weather.core.ui.FwTheme
import com.multiplatform.weather.core.ui.selectDayBackground
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import tdmultiplatform.weather.city.ui.generated.resources.Res
import tdmultiplatform.weather.city.ui.generated.resources.city_ui_failure_title
import tdmultiplatform.weather.city.ui.generated.resources.city_ui_select_another_city
import tdmultiplatform.weather.city.ui.generated.resources.city_ui_select_city
import tdmultiplatform.weather.city.ui.generated.resources.city_ui_select_city_title
import tdmultiplatform.weather.city.ui.generated.resources.city_ui_try_again
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

@Composable
fun CityWidget(
    onCitySelect: (City) -> Unit = {},
    onCityRemoved: (City) -> Unit = {},
    allowLastSelectionRemoval: Boolean = true,
) {
    val component = rememberCityComponent()
    val viewModel = kotlinInjectViewModel(
        create = component.cityWidgetViewModelFactory,
    )
    CityUi(
        state = viewModel.state,
        allowLastSelectionRemoval = allowLastSelectionRemoval,
        onCitySelect = onCitySelect,
        onCityRemoved = onCityRemoved,
        dispatch = viewModel::dispatch,
    )
}

@Composable
private fun rememberCityComponent(): CityComponent {
    val databaseComponent = LocalDatabaseComponent.current
    val dataStoreComponent = LocalDataSoreComponent.current

    val componentStore = LocalComponentStore.current

    return componentStore.store {
        createCityComponent(
            databaseComponent = databaseComponent,
            dataStoreComponent = dataStoreComponent,
        )
    }
}

@Composable
private fun CityUi(
    state: CityState,
    allowLastSelectionRemoval: Boolean,
    onCitySelect: (City) -> Unit,
    onCityRemoved: (City) -> Unit,
    dispatch: (CityEvent) -> Unit,
) {
    when (val uiState = state.uiState) {
        UiState.Loading -> CityLoadingView()
        is UiState.Failure -> CityFailureView(
            uiState = uiState,
            dispatch = dispatch,
        )
        is UiState.Success -> CitySuccessView(
            allowLastSelectionRemoval = allowLastSelectionRemoval,
            cities = uiState.cities,
            selectedCities = uiState.selectedCities,
            onCitySelect = onCitySelect,
            onCityRemoved = onCityRemoved,
            dispatch = dispatch,
        )
    }
    OnScreenStart { dispatch(CityEvent.OnScreenViewed) }
}

@Composable
private fun CityLoadingView() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
    ) {
        val placeholderModifier = Modifier
            .height(FwTheme.dimens.standard16)
            .background(
                color = FwTheme.colors.greys.light.copy(
                    alpha = .35f,
                ),
                shape = RoundedCornerShape(FwTheme.dimens.standard8),
            )
        Box(Modifier.fillMaxWidth(0.50f).then(placeholderModifier)) { SimmerEffect() }
        Spacer(Modifier.height(FwTheme.dimens.standard4))
        Box(Modifier.fillMaxWidth(0.75f).then(placeholderModifier)) { SimmerEffect() }
        Spacer(Modifier.height(FwTheme.dimens.standard4))
        Box(Modifier.fillMaxWidth(0.75f).then(placeholderModifier)) { SimmerEffect() }
        Spacer(Modifier.height(FwTheme.dimens.standard4))
        Box(Modifier.fillMaxWidth(0.75f).then(placeholderModifier)) { SimmerEffect() }
    }
}

@Composable
private fun BoxScope.SimmerEffect(
    widthOfShadowBrush: Int = 500,
    angleOfAxisY: Float = 270f,
    duration: Duration = 1.seconds,
) {
    val shimmerColors = listOf(
        FwTheme.colors.whites.primary.copy(alpha = 0.3f),
        FwTheme.colors.whites.primary.copy(alpha = 0.5f),
        FwTheme.colors.whites.primary.copy(alpha = 1f),
        FwTheme.colors.whites.primary.copy(alpha = 0.5f),
        FwTheme.colors.whites.primary.copy(alpha = 0.3f),
    )

    val transition = rememberInfiniteTransition(label = "Shimmering")

    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = (duration.toInt(DurationUnit.MILLISECONDS) + widthOfShadowBrush).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                delayMillis = 350,
                durationMillis = duration.toInt(DurationUnit.MILLISECONDS),
                easing = LinearEasing,
            ),
            repeatMode = RepeatMode.Restart,
        ),
        label = "Shimmer loading animation",
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(x = translateAnimation.value - widthOfShadowBrush, y = 0f),
        end = Offset(x = translateAnimation.value, y = angleOfAxisY),
    )
    Box(
        modifier = Modifier
            .matchParentSize()
            .background(
                color = Color.Transparent,
                shape = RoundedCornerShape(FwTheme.dimens.standard8),
            ),
    ) {
        Spacer(
            modifier = Modifier
                .matchParentSize()
                .background(
                    brush = brush,
                    shape = RoundedCornerShape(FwTheme.dimens.standard8),
                ),
        )
    }
}

@Composable
private fun CityFailureView(
    modifier: Modifier = Modifier,
    uiState: UiState.Failure,
    dispatch: (CityEvent) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
            .wrapContentHeight()
            .padding(horizontal = FwTheme.dimens.standard8),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(
                space = FwTheme.dimens.standard8,
            ),
        ) {
            Text(
                text = stringResource(Res.string.city_ui_failure_title),
                style = FwTheme.typography.titleSecondary,
            )
            val message = when (uiState) {
                is UiState.Failure.Res -> stringResource(uiState.stringResource)
                is UiState.Failure.Text -> uiState.message
            }
            Text(
                text = message,
                style = FwTheme.typography.bodySecondary,
            )
        }
        TdTextLinkBlue(
            textStyle = FwTheme.typography.bodySecondary.copy(
                fontWeight = FontWeight.Bold,
            ),
            textColor = FwTheme.colors.blues.secondary,
            pressedTextColor = FwTheme.colors.blues.primary,
            text = stringResource(Res.string.city_ui_try_again),
            onClick = { dispatch(CityEvent.OnTryAgainClicked) },
        )
    }
}

@Composable
private fun CitySuccessView(
    allowLastSelectionRemoval: Boolean,
    cities: List<City>,
    selectedCities: List<City>,
    onCitySelect: (City) -> Unit,
    onCityRemoved: (City) -> Unit,
    dispatch: (CityEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(horizontal = FwTheme.dimens.standard8)
            .fillMaxWidth(),
    ) {
        FwAutoCompleteCityInput(
            modifier = Modifier.fillMaxWidth(),
            possibleValues = cities,
            selectedValues = selectedCities,
            onValueChange = {
                dispatch(CityEvent.Operation.Add(it.value))
                onCitySelect(it.value)
            },
            onValueRemove = {
                val size = selectedCities.size
                if (size >= 1) {
                    if (allowLastSelectionRemoval) {
                        dispatch(CityEvent.Operation.Remove(it.value))
                        onCityRemoved(it.value)
                    } else if (size > 1) {
                        dispatch(CityEvent.Operation.Remove(it.value))
                        onCityRemoved(it.value)
                    }
                }
            },
            label = stringResource(Res.string.city_ui_select_city_title),
            placeholder = stringResource(
                resource = selectPlaceholderText(selectedCities),
            ),
        )
    }
}

internal fun selectPlaceholderText(cities: List<City>): StringResource {
    return when {
        cities.isEmpty() -> Res.string.city_ui_select_city
        else -> Res.string.city_ui_select_another_city
    }
}

private val country = Country(
    name = "Turkey",
    code = CountryCode.getOrThrow("TR"),
)

internal val city = City(
    id = 0L,
    name = "Istanbul",
    displayName = "Istanbul",
    country = country,
    location = Location(0.0, 0.0),
)

@Preview
@Composable
private fun CitySuccessViewPreview() {
    val cities = listOf(
        city,
        city.copy(id = 1, name = "Ankara", displayName = "Ankara"),
        city.copy(id = 2, name = "Adana", displayName = "Adana"),
        city.copy(id = 3, name = "Izmir", displayName = "Izmir"),
    )
    FwTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(brush = selectDayBackground())
                .padding(top = FwTheme.dimens.standard8),
        ) {
            CitySuccessView(
                allowLastSelectionRemoval = true,
                cities = cities,
                selectedCities = cities.takeLast(2),
                onCitySelect = { },
                onCityRemoved = { },
                dispatch = { },
            )
        }
    }
}

@Preview
@Composable
private fun CityFailureViewPreview() {
    FwTheme {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(vertical = FwTheme.dimens.standard8),
        ) {
            CityFailureView(
                uiState = UiState.Failure.Text(
                    message = "Sorry can not help you with that right now",
                ),
                dispatch = {},
            )
        }
    }
}
