package com.multiplatform.todo.onboarding.categories

import androidx.compose.runtime.Composable
import com.multiplatform.td.core.app.viewmodel.kotlinInjectViewModel
import com.multiplatform.todo.onboarding.inject.rememberOnboardingComponent

@Composable
internal fun CategoriesScreen() {
    val component = rememberOnboardingComponent()
    val viewModel = kotlinInjectViewModel(
        create = component.categoriesViewModelFactory,
    )
    CategoriesUi(viewModel.state, viewModel::dispatch)
}

@Composable
private fun CategoriesUi(
    state: CategoriesState,
    dispatch: (CategoriesEvent) -> Unit,
) {
    when (state.uiState) {
        UiState.Loading -> Unit // TODO implement this if needed
        is UiState.Failure -> Unit // TODO implement this if needed
        UiState.Success -> CategoriesSuccessView(state, dispatch)
    }
}

@Composable
private fun CategoriesSuccessView(
    state: CategoriesState,
    dispatch: (CategoriesEvent) -> Unit,
) {
    // TODO implement this ui starting user with onboarding where means initial load or cleaned up app state
}
