package com.multiplatform.weather.onboarding.city

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.multiplatform.td.core.app.composable.LocalComponentStore
import com.multiplatform.td.core.app.inject.store
import com.multiplatform.td.core.app.viewmodel.kotlinInjectViewModel
import com.multiplatform.td.core.datastore.composable.LocalDataSoreComponent
import com.multiplatform.td.core.navigation.composable.LocalNavigationComponent
import com.multiplatform.td.core.ui.effects.OnScreenStart
import com.multiplatform.td.core.ui.navbar.NavBarDefaults
import com.multiplatform.weather.city.CityWidget
import com.multiplatform.weather.core.ui.FwLoadingOverlay
import com.multiplatform.weather.core.ui.FwNavBar
import com.multiplatform.weather.core.ui.FwTheme
import com.multiplatform.weather.core.ui.selectDayBackground
import com.multiplatform.weather.onboarding.ContinueButton
import com.multiplatform.weather.onboarding.OnboardingFailureView
import com.multiplatform.weather.onboarding.OnboardingLayout
import com.multiplatform.weather.onboarding.UiState
import com.multiplatform.weather.onboarding.inject.OnboardingComponent
import com.multiplatform.weather.onboarding.inject.createOnboardingComponent
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import tdmultiplatform.weather.onboarding.ui.generated.resources.Res
import tdmultiplatform.weather.onboarding.ui.generated.resources.onboarding_ui_select_city
import tdmultiplatform.weather.onboarding.ui.generated.resources.onboarding_ui_select_city_body
import tdmultiplatform.weather.onboarding.ui.generated.resources.onboarding_ui_select_city_title

@Composable
internal fun SelectCityScreen() {
    val component = rememberOnboardingComponent()
    val viewModel = kotlinInjectViewModel(
        create = component.selectCityViewModelFactory,
    )
    SelectCityUi(viewModel.state, viewModel::dispatch)
}

@Composable
internal fun rememberOnboardingComponent(): OnboardingComponent {
    val navigationComponent = LocalNavigationComponent.current
    val dataStoreComponent = LocalDataSoreComponent.current

    val componentStore = LocalComponentStore.current

    return componentStore.store {
        createOnboardingComponent(
            dataStoreComponent = dataStoreComponent,
            navigationComponent = navigationComponent,
        )
    }
}

@Composable
private fun SelectCityUi(
    state: SelectCityState,
    dispatch: (SelectCityEvent) -> Unit,
) {
    when (val uiState = state.uiState) {
        UiState.Loading -> FwLoadingOverlay()
        is UiState.Failure -> OnboardingFailureView(uiState) {
            dispatch(SelectCityEvent.OnTryAgainClicked)
        }
        is UiState.Success -> SelectCitySuccessView(
            isEnabled = uiState.isContinueOrDoneEnabled,
            dispatch = dispatch,
        )
    }
    OnScreenStart { dispatch(SelectCityEvent.OnScreenViewed) }
}

@Composable
internal fun SelectCitySuccessView(
    isEnabled: Boolean,
    dispatch: (SelectCityEvent) -> Unit,
    widget: @Composable () -> Unit = @Composable {
        CityWidget(
            onCitySelect = { city -> dispatch(SelectCityEvent.Operation.Add(city)) },
            onCityRemoved = { city -> dispatch(SelectCityEvent.Operation.Remove(city)) },
        )
    },
) {
    Scaffold(
        topBar = {
            FwNavBar(
                title = NavBarDefaults.Title.Default.Res(
                    stringRes = Res.string.onboarding_ui_select_city_title,
                ),
            )
        },
    ) {
        OnboardingLayout(
            modifier = Modifier
                .background(selectDayBackground())
                .padding(it),
            title = stringResource(Res.string.onboarding_ui_select_city),
            body = stringResource(Res.string.onboarding_ui_select_city_body),
            action = {
                ContinueButton(isEnabled) {
                    dispatch(SelectCityEvent.OnContinueClicked)
                }
            },
        ) {
            widget()
        }
    }
}

@Preview
@Composable
private fun SelectCitySuccessViewPreview() {
    FwTheme {
        SelectCitySuccessView(
            isEnabled = false,
            dispatch = {},
        ) {
            Text("Widget Placeholder")
        }
    }
}
