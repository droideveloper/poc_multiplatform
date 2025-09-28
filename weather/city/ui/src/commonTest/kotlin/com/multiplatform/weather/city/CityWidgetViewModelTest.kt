package com.multiplatform.weather.city

import com.multiplatform.td.core.testing.AbstractDispatcherTest
import com.multiplatform.weather.city.loader.JsonDataLoader
import com.multiplatform.weather.city.repo.CityRepository
import com.multiplatform.weather.city.repo.SelectedCityRepository
import com.multiplatform.weather.city.usecase.DeleteCityUseCase
import com.multiplatform.weather.city.usecase.GetCitiesUseCase
import com.multiplatform.weather.city.usecase.GetSelectedCitiesUseCase
import com.multiplatform.weather.city.usecase.PopulateDatabaseUseCase
import com.multiplatform.weather.city.usecase.SaveCityUseCase
import dev.mokkery.answering.returns
import dev.mokkery.answering.sequentially
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import kotlinx.coroutines.test.runTest
import tdmultiplatform.weather.city.ui.generated.resources.Res
import tdmultiplatform.weather.city.ui.generated.resources.city_ui_failure_message
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class CityWidgetViewModelTest : AbstractDispatcherTest() {

    private var cityRepository = mock<CityRepository> {
        everySuspend { cities() } returns Result.success(listOf(City.Defaults))
    }

    private var selectedCityRepository = mock<SelectedCityRepository> {
        everySuspend { cities() } returns Result.success(listOf(City.Defaults))
        everySuspend { save(listOf(City.Defaults)) } returns Result.success(Unit)
    }

    private var jsonDataLoader = mock<JsonDataLoader> {
        everySuspend { invoke() } returns Result.success(Unit)
    }

    private val getCitiesUseCase by lazy { GetCitiesUseCase(cityRepository) }
    private val getSelectedCitiesUseCase by lazy { GetSelectedCitiesUseCase(selectedCityRepository) }
    private val populateDatabaseUseCase by lazy { PopulateDatabaseUseCase(jsonDataLoader) }
    private val saveCityUseCase by lazy { SaveCityUseCase(selectedCityRepository) }
    private val deleteCityUseCase by lazy { DeleteCityUseCase(selectedCityRepository) }

    private val viewModel by lazy {
        CityWidgetViewModel(
            getCitiesUseCase = getCitiesUseCase,
            getSelectedCitiesUseCase = getSelectedCitiesUseCase,
            populateDatabaseUseCase = populateDatabaseUseCase,
            saveCityUseCase = saveCityUseCase,
            deleteCityUseCase = deleteCityUseCase,
        )
    }

    @Test
    fun `given initial state and cities are empty than will populate and collect cities`() = runTest {
        cityRepository = mock<CityRepository> {
            everySuspend { cities() } sequentially {
                returns(Result.success(listOf()))
                returns(Result.success(listOf(City.Defaults)))
            }
        }

        selectedCityRepository = mock<SelectedCityRepository> {
            everySuspend { cities() } returns Result.failure(Throwable())
        }

        val state = viewModel.state

        assertTrue { state.uiState is UiState.Success }

        val uiState = state.uiState as UiState.Success
        assertContains(uiState.cities, City.Defaults)
        assertTrue { uiState.selectedCities.isEmpty() }

        verifySuspend {
            cityRepository.cities()
            jsonDataLoader()
            cityRepository.cities()
            selectedCityRepository.cities()
        }
    }

    @Test
    fun `given initial state and cities not empty than will collect cities`() = runTest {
        val state = viewModel.state

        assertTrue { state.uiState is UiState.Success }

        val uiState = state.uiState as UiState.Success
        assertContains(uiState.cities, City.Defaults)
        assertContains(uiState.selectedCities, City.Defaults)

        verifySuspend {
            cityRepository.cities()
            selectedCityRepository.cities()
        }
    }

    @Test
    fun `given initial load and return error with message than will display error`() = runTest {
        val error = Throwable("error message")
        cityRepository = mock<CityRepository> {
            everySuspend { cities() } returns Result.failure(error)
        }

        val state = viewModel.state

        assertTrue { state.uiState is UiState.Failure }
        assertTrue { state.uiState is UiState.Failure.Text }

        val uiState = state.uiState as UiState.Failure.Text
        assertEquals("error message", uiState.message)

        verifySuspend { cityRepository.cities() }
    }

    @Test
    fun `given initial load and return error than will display default error message`() = runTest {
        val error = Throwable()
        cityRepository = mock<CityRepository> {
            everySuspend { cities() } returns Result.failure(error)
        }

        val state = viewModel.state

        assertTrue { state.uiState is UiState.Failure }
        assertTrue { state.uiState is UiState.Failure.Res }

        val uiState = state.uiState as UiState.Failure.Res
        assertEquals(Res.string.city_ui_failure_message, uiState.stringResource)

        verifySuspend { cityRepository.cities() }
    }

    @Test
    fun `given add city than will save cities`() = runTest {
        selectedCityRepository = mock<SelectedCityRepository> {
            everySuspend { cities() } returns Result.failure(Throwable())
            everySuspend { save(listOf(City.Defaults)) } returns Result.success(Unit)
        }

        viewModel.dispatch(CityEvent.Operation.Add(City.Defaults))

        val state = viewModel.state
        assertTrue { state.uiState is UiState.Success }

        val uiState = state.uiState as UiState.Success
        assertContains(uiState.selectedCities, City.Defaults)

        verifySuspend {
            selectedCityRepository.cities()
            selectedCityRepository.save(listOf(City.Defaults))
        }
    }

    @Test
    fun `given add city failed than will print error`() = runTest {
        val error = Throwable()
        selectedCityRepository = mock<SelectedCityRepository> {
            everySuspend { cities() } returns Result.success(emptyList())
            everySuspend { save(listOf(City.Defaults)) } returns Result.failure(error)
        }

        viewModel.dispatch(CityEvent.Operation.Add(City.Defaults))

        verifySuspend {
            selectedCityRepository.save(listOf(City.Defaults))
        }
    }

    @Test
    fun `given remove city than will remove cities`() = runTest {
        selectedCityRepository = mock<SelectedCityRepository> {
            everySuspend { cities() } returns Result.success(listOf(City.Defaults))
            everySuspend { save(listOf()) } returns Result.success(Unit)
        }

        viewModel.dispatch(CityEvent.Operation.Remove(City.Defaults))

        val state = viewModel.state
        assertTrue { state.uiState is UiState.Success }

        val uiState = state.uiState as UiState.Success
        assertFalse { uiState.selectedCities.contains(City.Defaults) }

        verifySuspend {
            selectedCityRepository.cities()
            selectedCityRepository.save(listOf())
        }
    }

    @Test
    fun `given remove city failed than will log error`() = runTest {
        val error = Throwable()
        selectedCityRepository = mock<SelectedCityRepository> {
            everySuspend { cities() } returns Result.success(listOf(City.Defaults))
            everySuspend { save(listOf()) } returns Result.failure(error)
        }

        viewModel.dispatch(CityEvent.Operation.Remove(City.Defaults))

        verifySuspend {
            selectedCityRepository.save(listOf())
        }
    }
}
