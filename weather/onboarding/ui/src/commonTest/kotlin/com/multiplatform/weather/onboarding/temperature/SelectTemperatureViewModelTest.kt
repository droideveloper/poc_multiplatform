package com.multiplatform.weather.onboarding.temperature

import com.multiplatform.td.core.navigation.FeatureRouter
import com.multiplatform.td.core.testing.AbstractDispatcherTest
import com.multiplatform.weather.core.measure.Temperature
import com.multiplatform.weather.onboarding.OnboardingRoute
import com.multiplatform.weather.onboarding.UiState
import com.multiplatform.weather.settings.Settings
import com.multiplatform.weather.settings.repo.SettingsRepository
import com.multiplatform.weather.settings.usecase.GetSettingsUseCase
import com.multiplatform.weather.settings.usecase.SaveSettingsUseCase
import dev.mokkery.answering.returns
import dev.mokkery.answering.throws
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verify
import dev.mokkery.verifySuspend
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class SelectTemperatureViewModelTest : AbstractDispatcherTest() {

    private val setting by lazy {
        Settings.Defaults.copy(
            temperatureUnit = Temperature.Fahrenheit,
        )
    }

    private var repository = mock<SettingsRepository> {
        everySuspend { get() } returns Result.success(Settings.Defaults)
        everySuspend { save(setting) } returns Result.success(Unit)
    }

    private val getSettingsUseCase by lazy {  GetSettingsUseCase(repository) }
    private val saveSettingsUseCase by lazy { SaveSettingsUseCase(repository) }
    private val featureRouter = mock<FeatureRouter> {
        every { navigate(OnboardingRoute.SelectWindSpeed) } returns Unit
        every { back() } returns Unit
    }

    private val viewModel by lazy {
        SelectTemperatureViewModel(
            getSettingsUseCase = getSettingsUseCase,
            saveSettingsUseCase = saveSettingsUseCase,
            featureRouter = featureRouter,
        )
    }

    @Test
    fun `given temperature change will save new settings`() {
        viewModel.dispatch(SelectTemperatureEvent.OnChanged(Temperature.Fahrenheit))

        verifySuspend { repository.save(setting) }
    }

    @Test
    fun `given success will load default selection`() {
        assertEquals(viewModel.state.temperature, Temperature.Celsius)

        verifySuspend { repository.get() }
    }

    @Test
    fun `given try again clicked will load initial state`() {
        viewModel.dispatch(SelectTemperatureEvent.OnTryAgainClicked)

        verifySuspend { repository.get() }
    }

    @Test
    fun `given back clicked will navigate back`() {
        viewModel.dispatch(SelectTemperatureEvent.OnBackClicked)

        verify { featureRouter.back() }
    }

    @Test
    fun `given continue clicked will route to wind speed selection`() {
        viewModel.dispatch(SelectTemperatureEvent.OnContinueClicked)

        verify { featureRouter.navigate(OnboardingRoute.SelectWindSpeed) }
    }

    @Test
    fun `given initial load and failed to retrieve state then will save defaults`() {
        repository = mock {
            everySuspend { get() } throws Exception()
            everySuspend { save(Settings.Defaults) } returns Result.success(Unit)
        }

        viewModel.state

        verifySuspend {
            repository.get()
            repository.save(Settings.Defaults)
        }
    }

    @Test
    fun `given initial load and failed to retrieve state and save failed then show error ui`() {
        repository = mock {
            everySuspend { get() } throws Exception()
            everySuspend { save(Settings.Defaults) } throws Exception()
        }

        val uiState = viewModel.state.uiState

        verifySuspend {
            repository.get()
            repository.save(Settings.Defaults)
        }

        assertTrue { uiState is UiState.Failure }
    }
}