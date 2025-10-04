@file:OptIn(ExperimentalTime::class)

package com.multiplatform.weather.forecast.today

import com.multiplatform.td.core.navigation.FeatureRouter
import com.multiplatform.td.core.repository.Criteria
import com.multiplatform.td.core.testing.AbstractDispatcherTest
import com.multiplatform.weather.city.City
import com.multiplatform.weather.city.repo.SelectedCityRepository
import com.multiplatform.weather.city.usecase.GetSelectedCitiesUseCase
import com.multiplatform.weather.forecast.Forecast
import com.multiplatform.weather.forecast.ForecastRoute
import com.multiplatform.weather.forecast.LocalDateTimeProvider
import com.multiplatform.weather.forecast.repo.ForecastRepository
import com.multiplatform.weather.forecast.usecase.GetForecastUseCase
import com.multiplatform.weather.settings.SettingRoute
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
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.days
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

internal class ForecastViewModelTest : AbstractDispatcherTest() {

    private val selectedCityRepository = mock<SelectedCityRepository> {
        everySuspend { cities() } returns Result.success(listOf(City.Defaults))
        every { asFlow() } returns flowOf(Result.success(listOf(City.Defaults)))
    }

    private val forecastRepository = mock<ForecastRepository> {
        everySuspend {
            query(City.Defaults, Criteria.ofTimed(2.days))
        } returns Result.success(Forecast.Defaults)
    }

    private val settingsRepository = mock<SettingsRepository> {
        everySuspend { get() } returns Result.success(Settings.Defaults)
        every { asFlow() } returns flowOf(Result.success(Settings.Defaults))
    }

    private val featureRouter = mock<FeatureRouter> {
        every {
            navigate(SettingRoute.Settings)
        } returns Unit
        every {
            navigate(ForecastRoute.NextDays(City.Defaults.id))
        } returns Unit
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
        ForecastViewModel(
            getSelectedCitiesUseCase = GetSelectedCitiesUseCase(selectedCityRepository),
            getForecastUseCase = GetForecastUseCase(forecastRepository),
            getSettingsUseCase = GetSettingsUseCase(settingsRepository),
            featureRouter = featureRouter,
            localDateTimeProvider = localDateTimeProvider,
        )
    }

    @Test
    fun `given success than will initial state loaded`() = runTest {
        val state = viewModel.state

        viewModel.dispatch(ForecastEvent.OnScreenViewed)

        assertEquals(City.Defaults, state.city)
        assertEquals(Forecast.Defaults, state.forecast)
        assertEquals(Settings.Defaults, state.settings)
        assertEquals(times.first(), state.currentLocalDateTime)

        verifySuspend {
            selectedCityRepository.cities()
            forecastRepository.query(City.Defaults, Criteria.ofTimed(2.days))
        }

        verify {
            localDateTimeProvider()
            selectedCityRepository.asFlow()
            settingsRepository.asFlow()
        }
    }

    @Test
    fun `given city change than will load city forecast`() = runTest {
        viewModel.dispatch(ForecastEvent.OnCityChanged(City.Defaults))

        verifySuspend {
            repeat(2) {
                selectedCityRepository.cities()
                forecastRepository.query(City.Defaults, Criteria.ofTimed(2.days))
            }
        }

        verify {
            localDateTimeProvider()
            selectedCityRepository.asFlow()
            settingsRepository.asFlow()
        }
    }

    @Test
    fun `given ticked than will get time again`() = runTest {
        var state = viewModel.state

        assertEquals(times.first(), state.currentLocalDateTime)

        viewModel.dispatch(ForecastEvent.OnTick)
        state = viewModel.state

        assertEquals(times.last(), state.currentLocalDateTime)

        verify {
            repeat(2) {
                localDateTimeProvider()
            }
        }
    }

    @Test
    fun `given settings clicked than will navigate to settings route`() = runTest {
        viewModel.dispatch(ForecastEvent.OnSettingsClicked)

        verify { featureRouter.navigate(SettingRoute.Settings) }
    }

    @Test
    fun `given next days clicked than will navigate to next days forecast`() = runTest {
        viewModel.dispatch(ForecastEvent.OnNextDaysClicked(City.Defaults))

        verify { featureRouter.navigate(ForecastRoute.NextDays(City.Defaults.id)) }
    }
}
