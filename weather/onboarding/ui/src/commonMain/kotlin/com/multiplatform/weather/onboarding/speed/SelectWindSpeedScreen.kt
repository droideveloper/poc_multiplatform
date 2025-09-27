package com.multiplatform.weather.onboarding.speed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.multiplatform.td.core.app.viewmodel.kotlinInjectViewModel
import com.multiplatform.td.core.ui.effects.OnScreenStart
import com.multiplatform.td.core.ui.navbar.NavBarDefaults
import com.multiplatform.weather.core.measure.Speed
import com.multiplatform.weather.core.ui.FwLoadingOverlay
import com.multiplatform.weather.core.ui.FwNavBar
import com.multiplatform.weather.core.ui.FwTheme
import com.multiplatform.weather.core.ui.selectDayBackground
import com.multiplatform.weather.onboarding.DoneButton
import com.multiplatform.weather.onboarding.OnboardingFailureView
import com.multiplatform.weather.onboarding.OnboardingLayout
import com.multiplatform.weather.onboarding.UiState
import com.multiplatform.weather.onboarding.city.rememberOnboardingComponent
import com.multiplatform.weather.settings.WindSpeedSection
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import tdmultiplatform.weather.onboarding.ui.generated.resources.Res
import tdmultiplatform.weather.onboarding.ui.generated.resources.onboarding_ui_select_wind_speed_title
import tdmultiplatform.weather.onboarding.ui.generated.resources.onboarding_ui_select_wind_speed_unit
import tdmultiplatform.weather.onboarding.ui.generated.resources.onboarding_ui_select_wind_speed_unit_body

@Composable
internal fun SelectWindSpeedScreen() {
    val component = rememberOnboardingComponent()
    val viewModel = kotlinInjectViewModel(
        create = component.selectWindSpeedViewModelFactory,
    )
    SelectWindSpeedUi(viewModel.state, viewModel::dispatch)
}

@Composable
internal fun SelectWindSpeedUi(
    state: SelectWindSpeedState,
    dispatch: (SelectWindSpeedEvent) -> Unit,
) {
    when (val uiState = state.uiState) {
        UiState.Loading -> FwLoadingOverlay()
        is UiState.Failure -> OnboardingFailureView(uiState) {
            dispatch(SelectWindSpeedEvent.OnTryAgainClicked)
        }
        is UiState.Success -> SelectWindSpeedSuccessView(
            isEnabled = uiState.isContinueOrDoneEnabled,
            speed = state.speed,
            dispatch = dispatch,
        )
    }
    OnScreenStart { dispatch(SelectWindSpeedEvent.OnScreenViewed) }
}

@Composable
internal fun SelectWindSpeedSuccessView(
    isEnabled: Boolean,
    speed: Speed,
    dispatch: (SelectWindSpeedEvent) -> Unit,
) {
    Scaffold(
        topBar = {
            FwNavBar(
                title = NavBarDefaults.Title.Default.Res(
                    stringRes = Res.string.onboarding_ui_select_wind_speed_title,
                ),
                navAction = NavBarDefaults.ArrowBackAction {
                    dispatch(SelectWindSpeedEvent.OnBackClicked)
                },
            )
        },
    ) {
        OnboardingLayout(
            modifier = Modifier
                .background(selectDayBackground())
                .padding(it),
            title = stringResource(Res.string.onboarding_ui_select_wind_speed_unit),
            body = stringResource(Res.string.onboarding_ui_select_wind_speed_unit_body),
            action = {
                DoneButton(isEnabled) {
                    dispatch(SelectWindSpeedEvent.OnDoneClicked)
                }
            },
        ) {
            WindSpeedSection(
                speed = speed,
            ) {
                dispatch(SelectWindSpeedEvent.OnChanged(it))
            }
        }
    }
}

@Preview
@Composable
private fun SelectWindSpeedSuccessViewPreview() {
    FwTheme {
        SelectWindSpeedSuccessView(
            isEnabled = false,
            speed = Speed.KilometersPerHour,
            dispatch = {},
        )
    }
}
