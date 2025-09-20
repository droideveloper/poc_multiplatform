package com.multiplatform.todo.onboarding.categories

import androidx.lifecycle.viewModelScope
import com.multiplatform.td.core.injection.binding.ContributesViewModel
import com.multiplatform.td.core.injection.scopes.FeatureScope
import com.multiplatform.td.core.mvi.MviViewModel
import com.multiplatform.td.core.navigation.FeatureRouter
import com.multiplatform.todo.onboarding.usecase.GetOnboardingUseCase
import kotlinx.coroutines.launch

@ContributesViewModel(scope = FeatureScope::class)
internal class CategoriesViewModel(
    private val featureRouter: FeatureRouter,
    private val getOnboardingUseCase: GetOnboardingUseCase,
    private val setOnboardingUseCase: GetOnboardingUseCase,
) : MviViewModel<CategoriesEvent, CategoriesState>(
    initialState = CategoriesState(),
) {

    init {
        on<CategoriesEvent.OnScreenViewed> {
            // TODO implement analytics log or send event
        }

        viewModelScope.launch { collectInitialState() }
    }

    private suspend fun collectInitialState() = runCatching {
        // TODO implement proper initialization
    }
}
