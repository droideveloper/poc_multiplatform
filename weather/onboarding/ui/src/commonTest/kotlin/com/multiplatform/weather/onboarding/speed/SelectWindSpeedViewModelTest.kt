package com.multiplatform.weather.onboarding.speed

import com.multiplatform.td.core.app.inject.ComponentStore
import com.multiplatform.td.core.navigation.FeatureNavOptions
import com.multiplatform.td.core.navigation.FeatureRouter
import com.multiplatform.td.core.testing.AbstractDispatcherTest
import com.multiplatform.weather.core.measure.Speed
import com.multiplatform.weather.core.measure.Temperature
import com.multiplatform.weather.forecast.ForecastRoute
import com.multiplatform.weather.onboarding.Onboarding
import com.multiplatform.weather.onboarding.inject.OnboardingComponent
import com.multiplatform.weather.onboarding.repo.OnboardingRepository
import com.multiplatform.weather.onboarding.usecase.SaveOnboardingUseCase
import com.multiplatform.weather.settings.Settings
import com.multiplatform.weather.settings.repo.SettingsRepository
import com.multiplatform.weather.settings.usecase.GetSettingsUseCase
import com.multiplatform.weather.settings.usecase.SaveSettingsUseCase
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verify
import dev.mokkery.verifySuspend
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

internal class SelectWindSpeedViewModelTest : AbstractDispatcherTest() {

    private val setting by lazy {
        Settings.Defaults.copy(
            temperatureUnit = Temperature.Celsius,
        )
    }
    private val forecastOptions = FeatureNavOptions.Builder().apply {
        inclusive(true)
        popUpTo(Onboarding.Graph)
    }.build()

    private val componentStore = mock<ComponentStore> {
        every { removeIfExists(OnboardingComponent::class) } returns null
    }

    private var onboardingRepository = mock<OnboardingRepository> {
        everySuspend { get() } returns Result.success(true)
        everySuspend { save(true) } returns Result.success(Unit)
    }
    private var repository = mock<SettingsRepository> {
        everySuspend { get() } returns Result.success(Settings.Defaults)
        everySuspend { save(setting) } returns Result.success(Unit)
    }

    private val saveOnboardingUseCase by lazy { SaveOnboardingUseCase(onboardingRepository) }
    private val getSettingsUseCase by lazy {  GetSettingsUseCase(repository) }
    private val saveSettingsUseCase by lazy { SaveSettingsUseCase(repository) }
    private val featureRouter = mock<FeatureRouter> {
        every { navigate(ForecastRoute.Forecast, forecastOptions) } returns Unit
        every { back() } returns Unit
    }

    private val viewModel by lazy {
        SelectWindSpeedViewModel(
            getSettingsUseCase = getSettingsUseCase,
            saveSettingsUseCase = saveSettingsUseCase,
            saveOnboardingUseCase = saveOnboardingUseCase,
            featureRouter = featureRouter,
            componentStore = componentStore,
        )
    }

    @Test
    fun `given success will load settings selected and display`() {
        viewModel.dispatch(SelectWindSpeedEvent.OnScreenViewed)

        verifySuspend {
            repository.get()
        }

        assertEquals(viewModel.state.speed, setting.speedUnit)
    }

    @Test
    fun `given try again clicked will load settings selected and display`() {
        viewModel.dispatch(SelectWindSpeedEvent.OnScreenViewed)
        viewModel.dispatch(SelectWindSpeedEvent.OnTryAgainClicked)

        verifySuspend {
            repository.get()
            repository.get()
        }

        assertEquals(viewModel.state.speed, setting.speedUnit)
    }

    @Test
    fun `given back clicked will route to back`() {
        viewModel.dispatch(SelectWindSpeedEvent.OnBackClicked)

        verifySuspend {
            repository.get()
        }

        verify { featureRouter.back() }
    }

    @Test
    fun `given speed changed save settings selected and display`() {
        repository = mock<SettingsRepository> {
            everySuspend { get() } returns Result.success(Settings.Defaults)
            everySuspend { save(setting.copy(speedUnit = Speed.Knots)) } returns Result.success(Unit)
        }
        viewModel.dispatch(SelectWindSpeedEvent.OnChanged(Speed.Knots))

        verifySuspend {
            repository.get()
            repository.save(setting.copy(speedUnit = Speed.Knots))
        }

        assertNotEquals(viewModel.state.speed, setting.speedUnit)
    }

    @Test
    fun `given done clicked will navigate to forecast feature`() {
        viewModel.dispatch(SelectWindSpeedEvent.OnDoneClicked)

        verifySuspend {
            repository.get()
            onboardingRepository.save(true)
        }

        verify { featureRouter.navigate(ForecastRoute.Forecast, forecastOptions) }
    }
}
