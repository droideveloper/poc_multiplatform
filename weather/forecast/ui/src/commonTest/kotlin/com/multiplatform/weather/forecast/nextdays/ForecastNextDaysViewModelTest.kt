@file:OptIn(ExperimentalTime::class)

package com.multiplatform.weather.forecast.nextdays

import com.multiplatform.td.core.navigation.FeatureRouter
import com.multiplatform.td.core.repository.Criteria
import com.multiplatform.td.core.testing.AbstractDispatcherTest
import com.multiplatform.weather.city.City
import com.multiplatform.weather.city.repo.SelectedCityRepository
import com.multiplatform.weather.city.usecase.GetSelectedCitiesUseCase
import com.multiplatform.weather.forecast.Forecast
import com.multiplatform.weather.forecast.LocalDateTimeProvider
import com.multiplatform.weather.forecast.repo.ForecastRepository
import com.multiplatform.weather.forecast.today.UiState
import com.multiplatform.weather.forecast.usecase.GetForecastUseCase
import com.multiplatform.weather.settings.Settings
import com.multiplatform.weather.settings.repo.SettingsRepository
import com.multiplatform.weather.settings.usecase.GetSettingsUseCase
import dev.mokkery.answering.returns
import dev.mokkery.answering.sequentially
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verify
import dev.mokkery.verifySuspend
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.days
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

internal class ForecastNextDaysViewModelTest : AbstractDispatcherTest() {

    private val selectedCityRepository = mock<SelectedCityRepository> {
        everySuspend { cities() } returns Result.success(listOf(City.Defaults))
    }

    private val forecastRepository = mock<ForecastRepository> {
        everySuspend {
            query(City.Defaults, Criteria.ofTimed(2.days))
        } returns Result.success(Forecast.Defaults)
    }

    private val settingsRepository = mock<SettingsRepository> {
        everySuspend { get() } returns Result.success(Settings.Defaults)
    }

    private val featureRouter = mock<FeatureRouter> {
        every { back() } returns Unit
    }

    private val times by lazy {
        setOf(
            Instant.parse("2025-10-04T05:00:00Z").toLocalDateTime(TimeZone.UTC),
            Instant.parse("2025-10-04T05:10:00Z").toLocalDateTime(TimeZone.UTC),
        )
    }

    private val localDateTimeProvider = mock<LocalDateTimeProvider> {
        every { invoke() } sequentially {
            returns(times.first())
            returns(times.last())
        }
    }

    private val viewModel by lazy {
        ForecastNextDaysViewModel(
            getSelectedCitiesUseCase = GetSelectedCitiesUseCase(selectedCityRepository),
            getForecastUseCase = GetForecastUseCase(forecastRepository),
            getSettingsUseCase = GetSettingsUseCase(settingsRepository),
            featureRouter = featureRouter,
            localDateTimeProvider = localDateTimeProvider,
            selectedCityId = City.Defaults.id,
        )
    }

    @Test
    fun `given initial state than will load data`() = runTest {
        val state = viewModel.state
        viewModel.dispatch(ForecastNextDaysEvent.OnScreenViewed)

        assertTrue { state.uiState is UiState.Success }
        assertEquals(City.Defaults, state.city)
        assertEquals(Forecast.Defaults, state.forecast)
        assertEquals(Settings.Defaults, state.settings)
        assertEquals(times.first(), state.currentLocalDateTime)

        verifySuspend {
            selectedCityRepository.cities()
            forecastRepository.query(City.Defaults, Criteria.ofTimed(2.days))
            settingsRepository.get()
        }
        verify { localDateTimeProvider() }
    }

    @Test
    fun `given back clicked than will route back`() = runTest {
        viewModel.dispatch(ForecastNextDaysEvent.OnBackClicked)

        verify { featureRouter.back() }
    }

    @Test
    fun `given ticked than will get time again`() = runTest {
        var state = viewModel.state

        assertEquals(times.first(), state.currentLocalDateTime)

        viewModel.dispatch(ForecastNextDaysEvent.OnTick)
        state = viewModel.state

        assertEquals(times.last(), state.currentLocalDateTime)

        verify {
            repeat(2) {
                localDateTimeProvider()
            }
        }
    }
}
