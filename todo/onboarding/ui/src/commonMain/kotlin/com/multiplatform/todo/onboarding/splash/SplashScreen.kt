package com.multiplatform.todo.onboarding.splash

import androidx.compose.runtime.Composable
import com.multiplatform.td.core.app.viewmodel.kotlinInjectViewModel
import com.multiplatform.todo.onboarding.inject.rememberOnboardingComponent

@Composable
internal fun SplashScreen() {
    val component = rememberOnboardingComponent()
    val viewModel = kotlinInjectViewModel(
        create = component.splashViewModelFactory,
    )
    SplashUi(viewModel.state, viewModel::dispatch)
}

@Composable
private fun SplashUi(
    state: SplashState,
    dispatch: (SplashEvent) -> Unit,
) {
    when (state.uiState) {
        UiState.Loading -> Unit // TODO implement this if needed
        is UiState.Failure -> Unit // TODO implement this if needed
        UiState.Success -> SplashSuccessView(state, dispatch)
    }
}

@Composable
private fun SplashSuccessView(
    state: SplashState,
    dispatch: (SplashEvent) -> Unit,
) {
    // TODO implement this, will be implemented later on state where onboarding will be checked in every state of ui
}
