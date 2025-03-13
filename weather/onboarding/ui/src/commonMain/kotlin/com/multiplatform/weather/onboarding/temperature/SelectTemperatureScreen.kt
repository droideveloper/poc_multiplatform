package com.multiplatform.weather.onboarding.temperature

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.multiplatform.td.core.app.viewmodel.kotlinInjectViewModel
import com.multiplatform.td.core.ui.effects.OnScreenStart
import com.multiplatform.td.core.ui.navbar.NavBarDefaults
import com.multiplatform.weather.core.ui.FwLoadingOverlay
import com.multiplatform.weather.core.ui.selectDayBackground
import com.multiplatform.weather.core.measure.Temperature
import com.multiplatform.weather.core.ui.FwNavBar
import com.multiplatform.weather.onboarding.ContinueButton
import com.multiplatform.weather.onboarding.OnboardingFailureView
import com.multiplatform.weather.onboarding.OnboardingLayout
import com.multiplatform.weather.onboarding.UiState
import com.multiplatform.weather.onboarding.city.rememberOnboardingComponent
import com.multiplatform.weather.settings.TemperatureSection
import org.jetbrains.compose.resources.stringResource
import tdmultiplatform.weather.onboarding.ui.generated.resources.Res
import tdmultiplatform.weather.onboarding.ui.generated.resources.onboarding_ui_select_temperature_title
import tdmultiplatform.weather.onboarding.ui.generated.resources.onboarding_ui_select_temperature_unit
import tdmultiplatform.weather.onboarding.ui.generated.resources.onboarding_ui_select_temperature_unit_body

@Composable
internal fun SelectTemperatureScreen() {
    val component = rememberOnboardingComponent()
    val viewModel = kotlinInjectViewModel(
        create = component.selectTemperatureViewModelFactory,
    )
    SelectTemperatureUi(viewModel.state, viewModel::dispatch)
}

@Composable
internal fun SelectTemperatureUi(
    state: SelectTemperatureState,
    dispatch: (SelectTemperatureEvent) -> Unit,
) {
    when (val uiState = state.uiState) {
        UiState.Loading -> FwLoadingOverlay()
        is UiState.Failure -> OnboardingFailureView(uiState) {
            dispatch(SelectTemperatureEvent.OnTryAgainClicked)
        }
        is UiState.Success -> SelectTemperatureSuccessView(
            isEnabled = uiState.isContinueOrDoneEnabled,
            temperature = state.temperature,
            dispatch = dispatch,
        )
    }
    OnScreenStart { dispatch(SelectTemperatureEvent.OnScreenViewed) }
}


@Composable
internal fun SelectTemperatureSuccessView(
    isEnabled: Boolean,
    temperature: Temperature,
    dispatch: (SelectTemperatureEvent) -> Unit,
) {
    Scaffold(
        topBar = {
            FwNavBar(
                title = NavBarDefaults.Title.Default.Res(
                    stringRes = Res.string.onboarding_ui_select_temperature_title,
                ),
                navAction = NavBarDefaults.ArrowBackAction {
                    dispatch(SelectTemperatureEvent.OnBackClicked)
                },
            )
        }
    ) {
        OnboardingLayout(
            modifier = Modifier
                .background(selectDayBackground())
                .padding(it),
            title = stringResource(Res.string.onboarding_ui_select_temperature_unit),
            body = stringResource(Res.string.onboarding_ui_select_temperature_unit_body),
            action = {
                ContinueButton(isEnabled) {
                    dispatch(SelectTemperatureEvent.OnContinueClicked)
                }
            },
        ) {
            TemperatureSection(
                temperature = temperature,
            ) { temperature ->
                dispatch(SelectTemperatureEvent.OnChanged(temperature))
            }
        }
    }
}