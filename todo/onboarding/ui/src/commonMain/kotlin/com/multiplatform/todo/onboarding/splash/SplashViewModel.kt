package com.multiplatform.todo.onboarding.splash

import androidx.lifecycle.viewModelScope
import com.multiplatform.td.core.injection.binding.ContributesViewModel
import com.multiplatform.td.core.injection.scopes.FeatureScope
import com.multiplatform.td.core.mvi.MviViewModel
import com.multiplatform.td.core.navigation.FeatureRouter
import com.multiplatform.todo.onboarding.usecase.GetOnboardingUseCase
import kotlinx.coroutines.launch

@ContributesViewModel(scope = FeatureScope::class)
internal class SplashViewModel(
    private val featureRouter: FeatureRouter,
    private val getOnboardingUseCase: GetOnboardingUseCase,
) : MviViewModel<SplashEvent, SplashState>(
     initialState = SplashState()
) {

    init {
        on<SplashEvent.OnScreenViewed> {
            // TODO analytics log in here
        }
        viewModelScope.launch { collectInitialState() }
    }

    private suspend fun collectInitialState() = runCatching {
        val result = getOnboardingUseCase()

        result.fold(
            onSuccess = {
                // check value and decide where to navigate
                // a navigate to home
                // b start onboarding
            },
            onFailure = {
                // show error ? try again ? what we gone try again :D
            }
        )
    }
}
