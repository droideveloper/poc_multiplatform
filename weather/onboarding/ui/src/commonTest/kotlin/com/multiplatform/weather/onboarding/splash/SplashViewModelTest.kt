package com.multiplatform.weather.onboarding.splash

import com.multiplatform.td.core.app.inject.ComponentStore
import com.multiplatform.td.core.navigation.FeatureNavOptions
import com.multiplatform.td.core.navigation.FeatureRouter
import com.multiplatform.td.core.testing.AbstractDispatcherTest
import com.multiplatform.weather.forecast.ForecastRoute
import com.multiplatform.weather.onboarding.Onboarding
import com.multiplatform.weather.onboarding.OnboardingRoute
import com.multiplatform.weather.onboarding.inject.OnboardingComponent
import com.multiplatform.weather.onboarding.repo.OnboardingRepository
import com.multiplatform.weather.onboarding.usecase.GetOnboardingUseCase
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verify
import dev.mokkery.verifySuspend
import kotlin.test.Test

internal class SplashViewModelTest : AbstractDispatcherTest() {

    private var onboardingRepository = mock<OnboardingRepository> {
        everySuspend { get() } returns Result.success(true)
        everySuspend { save(true) } returns Result.success(Unit)
    }
    private val defaultOptions = FeatureNavOptions.Builder().build()
    private val forecastOptions = FeatureNavOptions.Builder().apply {
        inclusive(true)
        popUpTo(Onboarding.Graph)
    }.build()

    private val componentStore = mock<ComponentStore> {
        every { removeIfExists(clazz = OnboardingComponent::class) } returns null
    }

    private val featureRouter = mock<FeatureRouter> {
        every { navigate(OnboardingRoute.SelectCity, defaultOptions) } returns Unit
        every { navigate(ForecastRoute.Forecast, forecastOptions) } returns Unit
    }

    private val viewModel by lazy {
        SplashViewModel(
            getOnboardingUseCase = GetOnboardingUseCase(onboardingRepository),
            featureRouter = featureRouter,
            componentStore = componentStore,
        )
    }

    @Test
    fun given_screen_loaded_and_screen_viewed_then_when_success_will_navigate_to_forecast_feature() {
        viewModel.dispatch(SplashEvent.OnScreenViewed)

        verifySuspend { onboardingRepository.get() }
        verify { featureRouter.navigate(ForecastRoute.Forecast, forecastOptions) }
    }

    @Test
    fun given_screen_loaded_and_screen_viewed_then_when_success_will_navigate_to_select_city() {
        onboardingRepository = mock<OnboardingRepository> {
            everySuspend { get() } returns Result.success(false)
        }

        viewModel.dispatch(SplashEvent.OnScreenViewed)

        verifySuspend { onboardingRepository.get() }
        verify { featureRouter.navigate(OnboardingRoute.SelectCity, defaultOptions) }
    }
}
