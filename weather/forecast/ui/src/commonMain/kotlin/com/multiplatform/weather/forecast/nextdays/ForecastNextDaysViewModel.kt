@file:OptIn(ExperimentalTime::class)

package com.multiplatform.weather.forecast.nextdays

import androidx.lifecycle.viewModelScope
import com.multiplatform.td.core.injection.binding.ContributesViewModel
import com.multiplatform.td.core.injection.scopes.FeatureScope
import com.multiplatform.td.core.mvi.MviViewModel
import com.multiplatform.td.core.navigation.FeatureRouter
import com.multiplatform.weather.city.usecase.GetSelectedCitiesUseCase
import com.multiplatform.weather.forecast.LocalDateTimeProvider
import com.multiplatform.weather.forecast.today.UiState
import com.multiplatform.weather.forecast.usecase.GetForecastUseCase
import com.multiplatform.weather.settings.usecase.GetSettingsUseCase
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import kotlin.time.ExperimentalTime

@ContributesViewModel(scope = FeatureScope::class)
internal class ForecastNextDaysViewModel(
    private val getSelectedCitiesUseCase: GetSelectedCitiesUseCase,
    private val getForecastUseCase: GetForecastUseCase,
    private val getSettingsUseCase: GetSettingsUseCase,
    private val featureRouter: FeatureRouter,
    private val localDateTimeProvider: LocalDateTimeProvider,
    @Assisted private val selectedCityId: Long,
) : MviViewModel<ForecastNextDaysEvent, ForecastNextDaysState>(
    initialState = ForecastNextDaysState(),
) {

    init {
        on<ForecastNextDaysEvent.OnScreenViewed> {
            // do something with it
        }
        onClick<ForecastNextDaysEvent.OnBackClicked> {
            featureRouter.back()
        }
        on<ForecastNextDaysEvent.OnTick> {
            state = state.copy(
                currentLocalDateTime = now(),
            )
        }
        viewModelScope.launch { collectInitialState() }
    }

    private suspend fun collectInitialState() = runCatching {
        val cities = getSelectedCitiesUseCase().getOrThrow()
        val city = cities.first { it.id == selectedCityId }
        val settings = getSettingsUseCase().getOrThrow()
        val forecast = getForecastUseCase(city).getOrThrow()

        state = state.copy(
            uiState = UiState.Success,
            city = city,
            settings = settings,
            forecast = forecast,
            currentLocalDateTime = now(),
        )
    }

    private fun now() = localDateTimeProvider()
}
