package com.multiplatform.weather.onboarding.splash

import androidx.lifecycle.viewModelScope
import com.multiplatform.td.core.app.inject.ComponentStore
import com.multiplatform.td.core.app.inject.remove
import com.multiplatform.td.core.injection.binding.ContributesViewModel
import com.multiplatform.td.core.injection.scopes.FeatureScope
import com.multiplatform.td.core.mvi.MviViewModel
import com.multiplatform.td.core.navigation.FeatureNavOptions
import com.multiplatform.td.core.navigation.FeatureRouter
import com.multiplatform.weather.forecast.ForecastRoute
import com.multiplatform.weather.onboarding.Onboarding
import com.multiplatform.weather.onboarding.OnboardingRoute
import com.multiplatform.weather.onboarding.inject.OnboardingComponent
import com.multiplatform.weather.onboarding.usecase.GetOnboardingUseCase
import kotlinx.coroutines.launch

@ContributesViewModel(scope = FeatureScope::class)
internal class SplashViewModel(
    private val getOnboardingUseCase: GetOnboardingUseCase,
    private val featureRouter: FeatureRouter,
    private val componentStore: ComponentStore,
) : MviViewModel<SplashEvent, SplashState>(
    initialState = SplashState(),
) {

    init {
        on<SplashEvent.OnScreenViewed> {
            // TODO implement log and other stuff in here
        }
        viewModelScope.launch { collectInitialState() }
    }

    private suspend fun collectInitialState() = runCatching {
        val result = getOnboardingUseCase()
        result.fold(
            onSuccess = { navigateToHomeOrOnboarding(it) },
            onFailure = { navigateToHomeOrOnboarding(false) },
        )
    }

    private fun navigateToHomeOrOnboarding(isOnboardingCompleted: Boolean) {
        val route = when {
            isOnboardingCompleted -> ForecastRoute.Forecast
            else -> OnboardingRoute.SelectCity
        }
        val options = when {
            isOnboardingCompleted -> FeatureNavOptions.Builder().apply {
                inclusive(true)
                popUpTo(Onboarding.Graph)
            }.build()

            else -> FeatureNavOptions.Builder().build()
        }
        featureRouter.navigate(route, options).also {
            if (isOnboardingCompleted) {
                // if there is onboarding component created when we navigate it will remove reference from store
                componentStore.remove<OnboardingComponent>()
            }
        }
    }
}
