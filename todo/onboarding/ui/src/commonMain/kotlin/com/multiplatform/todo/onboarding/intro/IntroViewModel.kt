package com.multiplatform.todo.onboarding.intro

import androidx.lifecycle.viewModelScope
import com.multiplatform.td.core.injection.binding.ContributesViewModel
import com.multiplatform.td.core.injection.scopes.FeatureScope
import com.multiplatform.td.core.mvi.MviViewModel
import com.multiplatform.td.core.navigation.FeatureRouter
import kotlinx.coroutines.launch

@ContributesViewModel(scope = FeatureScope::class)
internal class IntroViewModel(
    private val featureRouter: FeatureRouter,
) : MviViewModel<IntroEvent, IntroState>(
    initialState = IntroState(),
) {

    init {
        on<IntroEvent.OnScreenViewed> {
            // TODO log analytics or fire event for analytics
        }
        // TODO implement other event in here

        viewModelScope.launch { collectInitialState() }
    }

    private suspend fun collectInitialState() = runCatching {
        // TODO implement initial use case
    }
}
