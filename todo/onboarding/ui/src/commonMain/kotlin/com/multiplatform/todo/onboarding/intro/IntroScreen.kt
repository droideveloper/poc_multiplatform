package com.multiplatform.todo.onboarding.intro

import androidx.compose.runtime.Composable
import com.multiplatform.td.core.app.viewmodel.kotlinInjectViewModel
import com.multiplatform.todo.onboarding.inject.rememberOnboardingComponent

@Composable
internal fun IntroScreen() {
    val component = rememberOnboardingComponent()
    val viewModel = kotlinInjectViewModel(
        create = component.introViewModelFactory,
    )
    IntroUi(viewModel.state, viewModel::dispatch)
}

@Composable
private fun IntroUi(
    state: IntroState,
    dispatch: (IntroEvent) -> Unit,
) {
    when (state.uiState) {
        UiState.Loading -> Unit // TODO implement this if needed
        is UiState.Failure -> Unit // TODO implement this if needed
        UiState.Success -> IntroSuccessView(state, dispatch)
    }
}

@Composable
private fun IntroSuccessView(
    state: IntroState,
    dispatch: (IntroEvent) -> Unit,
) {
    // TODO implement this ui starting user with onboarding where means initial load or cleaned up app state
}
