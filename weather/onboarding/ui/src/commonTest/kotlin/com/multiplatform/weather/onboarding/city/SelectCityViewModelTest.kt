package com.multiplatform.weather.onboarding.city

import com.multiplatform.td.core.navigation.FeatureRouter
import com.multiplatform.td.core.testing.AbstractDispatcherTest
import com.multiplatform.weather.city.City
import com.multiplatform.weather.city.repo.SelectedCityRepository
import com.multiplatform.weather.city.usecase.GetSelectedCitiesUseCase
import com.multiplatform.weather.onboarding.OnboardingRoute
import com.multiplatform.weather.onboarding.UiState
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verify
import dev.mokkery.verifySuspend
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class SelectCityViewModelTest : AbstractDispatcherTest() {

    private var repository = mock<SelectedCityRepository> {
        everySuspend { cities() } returns Result.success(listOf(City.Defaults))
        every { asFlow() } returns flowOf(Result.success(listOf(City.Defaults)))
    }

    private val getSelectedCitiesUseCase by lazy { GetSelectedCitiesUseCase(repository) }

    private val featureRouter = mock<FeatureRouter> {
        every { navigate(OnboardingRoute.SelectTemperature) } returns Unit
    }

    private val viewModel by lazy {
        SelectCityViewModel(
            getSelectedCitiesUseCase = getSelectedCitiesUseCase,
            featureRouter = featureRouter,
        )
    }

    @Test
    fun `given initial load than will load selected cities`() = runTest {
        val size = viewModel.selectedCities.size

        assertTrue { size != 0 }
        assertContains(viewModel.selectedCities, City.Defaults)

        verifySuspend { repository.cities() }
    }

    @Test
    fun `given on retry clicked than will reload data`() = runTest {
        viewModel.dispatch(SelectCityEvent.OnTryAgainClicked)

        val size = viewModel.selectedCities.size

        assertTrue { size != 0 }

        verifySuspend {
            repeat(2) {
                repository.cities()
            }
        }
    }

    @Test
    fun `given on continue clicked than will navigate to next screen`() = runTest {
        viewModel.dispatch(SelectCityEvent.OnContinueClicked)

        verify { featureRouter.navigate(OnboardingRoute.SelectTemperature) }
    }

    @Test
    fun `given on add clicked than will add city to cache`() = runTest {
        val newCity = City.Defaults.copy(id = Long.MIN_VALUE)

        viewModel.dispatch(SelectCityEvent.Operation.Add(newCity))

        assertContains(viewModel.selectedCities, newCity)
    }

    @Test
    fun `given on remove clicked than will remove from cache`() = runTest {
        viewModel.dispatch(SelectCityEvent.Operation.Remove(City.Defaults))

        val uiState = viewModel.state.uiState as UiState.Success

        assertTrue { viewModel.selectedCities.isEmpty() }
        assertFalse { uiState.isContinueOrDoneEnabled }
    }
}
