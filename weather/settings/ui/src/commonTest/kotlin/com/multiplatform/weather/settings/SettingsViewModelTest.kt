package com.multiplatform.weather.settings

import com.multiplatform.td.core.datastore.KeyedValueDataStoreException
import com.multiplatform.td.core.environment.AppVersion
import com.multiplatform.td.core.navigation.FeatureRouter
import com.multiplatform.td.core.testing.AbstractDispatcherTest
import com.multiplatform.weather.city.City
import com.multiplatform.weather.city.repo.SelectedCityRepository
import com.multiplatform.weather.city.usecase.GetSelectedCitiesUseCase
import com.multiplatform.weather.core.measure.Speed
import com.multiplatform.weather.core.measure.Temperature
import com.multiplatform.weather.settings.repo.SettingsRepository
import com.multiplatform.weather.settings.usecase.GetSettingsUseCase
import com.multiplatform.weather.settings.usecase.SaveSettingsUseCase
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verify
import dev.mokkery.verify.VerifyMode
import dev.mokkery.verifySuspend
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class SettingsViewModelTest : AbstractDispatcherTest() {

    private val setting by lazy {
        Settings.Defaults.copy(
            temperatureUnit = Temperature.Fahrenheit,
        )
    }

    private var repository = mock<SettingsRepository> {
        everySuspend { get() } returns Result.success(setting)
        everySuspend { save(setting) } returns Result.success(Unit)
    }

    private var selectedCitiesRepository = mock<SelectedCityRepository> {
        everySuspend { cities() } returns Result.success(listOf(City.Defaults))
        every { asFlow() } returns flowOf(Result.success(listOf(City.Defaults)))
    }

    private val getSettingsUseCase by lazy { GetSettingsUseCase(repository) }
    private val saveSettingsUseCase by lazy { SaveSettingsUseCase(repository) }
    private val getSelectedCitiesUseCase by lazy { GetSelectedCitiesUseCase(selectedCitiesRepository) }

    private val featureRouter = mock<FeatureRouter> {
        every { back() } returns Unit
    }

    private val viewModel by lazy {
        SettingsViewModel(
            getSettingsUseCase = getSettingsUseCase,
            saveSettingsUseCase = saveSettingsUseCase,
            getSelectedCitiesUseCase = getSelectedCitiesUseCase,
            featureRouter = featureRouter,
            version = AppVersion("NA"),
        )
    }

    @Test
    fun `given initial load will update state from previous selections`() = runTest {
        val state = viewModel.state

        assertTrue { state.uiState is UiState.Success }
        val uiState = state.uiState as UiState.Success
        assertEquals(setting, uiState.settings.value)

        verifySuspend {
            repository.get()
            selectedCitiesRepository.cities()
        }
    }

    @Test
    fun `given could not get settings will update state from defaults`() = runTest {
        val error = KeyedValueDataStoreException.NotFoundException("settings")
        repository = mock<SettingsRepository> {
            everySuspend { get() } returns Result.failure(error)
        }
        val state = viewModel.state

        assertTrue { state.uiState is UiState.Success }
        val uiState = state.uiState as UiState.Success
        assertEquals(Settings.Defaults, uiState.settings.value)

        verifySuspend {
            repository.get()
            selectedCitiesRepository.cities()
        }
    }

    @Test
    fun `given on back clicked than will route back`() = runTest {
        viewModel.dispatch(SettingsEvent.OnBackClicked)

        verify { featureRouter.back() }
    }

    @Test
    fun `given on back clicked and cities are empty than will NOT route back`() = runTest {
        viewModel.selectedCities = emptyList()
        viewModel.dispatch(SettingsEvent.OnBackClicked)

        verify(mode = VerifyMode.not) {
            featureRouter.back()
        }
    }

    @Test
    fun `given on temperature changed than will save new setting`() = runTest {
        val newSetting = setting.copy(Temperature.Celsius)
        repository = mock<SettingsRepository> {
            everySuspend { get() } returns Result.success(setting)
            everySuspend { save(newSetting) } returns Result.success(Unit)
        }

        viewModel.dispatch(SettingsEvent.OnTemperature.Change(Temperature.Celsius))

        val uiState = viewModel.state.uiState as UiState.Success
        assertEquals(Temperature.Celsius, uiState.settings.value.temperatureUnit)

        verifySuspend { repository.save(newSetting) }
    }

    @Test
    fun `given on days increased than will save new settings`() = runTest {
        val newSetting = setting.copy(days = setting.days + 1)
        repository = mock<SettingsRepository> {
            everySuspend { get() } returns Result.success(setting)
            everySuspend { save(newSetting) } returns Result.success(Unit)
        }

        viewModel.dispatch(SettingsEvent.OnDays.Increment)

        val uiState = viewModel.state.uiState as UiState.Success
        assertEquals(newSetting.days, uiState.settings.value.days)

        verifySuspend { repository.save(newSetting) }
    }

    @Test
    fun `given on days decreased than will save new settings`() = runTest {
        val newSetting = setting.copy(days = setting.days - 1)
        repository = mock<SettingsRepository> {
            everySuspend { get() } returns Result.success(setting)
            everySuspend { save(newSetting) } returns Result.success(Unit)
        }

        viewModel.dispatch(SettingsEvent.OnDays.Decrement)

        val uiState = viewModel.state.uiState as UiState.Success
        assertEquals(newSetting.days, uiState.settings.value.days)

        verifySuspend { repository.save(newSetting) }
    }

    @Test
    fun `given on wind changed than will save new setting`() = runTest {
        val newSetting = setting.copy(speedUnit = Speed.MilesPerHour)
        repository = mock<SettingsRepository> {
            everySuspend { get() } returns Result.success(setting)
            everySuspend { save(newSetting) } returns Result.success(Unit)
        }

        viewModel.dispatch(SettingsEvent.OnSpeed.Change(Speed.MilesPerHour))

        val uiState = viewModel.state.uiState as UiState.Success
        assertEquals(Speed.MilesPerHour, uiState.settings.value.speedUnit)

        verifySuspend { repository.save(newSetting) }
    }

    @Test
    fun `given on add new city than will cache into cities`() = runTest {
        val newCity = City.Defaults.copy(id = Long.MIN_VALUE)

        viewModel.dispatch(SettingsEvent.Operation.Add(newCity))

        assertContains(viewModel.selectedCities, newCity)
    }

    @Test
    fun `given on remove city than will remove cache from cities`() = runTest {
        viewModel.dispatch(SettingsEvent.Operation.Remove(City.Defaults))

        assertFalse { viewModel.selectedCities.contains(City.Defaults) }
    }

    @Test
    fun `given on try again clicked than will reload initial state`() = runTest {
        viewModel.dispatch(SettingsEvent.OnTryAgainClicked)

        verifySuspend {
            repeat(2) {
                repository.get()
                selectedCitiesRepository.cities()
            }
        }
    }
}
