package com.multiplatform.todo.calendar

import androidx.lifecycle.viewModelScope
import com.multiplatform.td.core.injection.binding.ContributesViewModel
import com.multiplatform.td.core.injection.scopes.FeatureScope
import com.multiplatform.td.core.mvi.MviViewModel
import com.multiplatform.td.core.navigation.FeatureRouter
import com.multiplatform.todo.calendar.usecase.GetWeekUseCase
import com.multiplatform.todo.tasks.AppRoute
import com.multiplatform.todo.tasks.usecase.GetTasksForDateUseCase
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

@ContributesViewModel(scope = FeatureScope::class)
internal class CalendarViewModel(
    private val getTasksForDateUseCase: GetTasksForDateUseCase,
    private val getWeekUseCase: GetWeekUseCase,
    private val featureRouter: FeatureRouter,
) : MviViewModel<CalendarEvent, CalendarState>(
    initialState = CalendarState()
) {

    private val today by lazy {
        LocalDateTime.Formats.ISO.parse("2025-03-04T19:00:00")
    }

    init {
        on<CalendarEvent.OnScreenViewed> {
            // TODO send analytics or track event
        }
        onClick<CalendarEvent.OnDateClicked> {
           collectInitialState(date = it.date)
        }
        onClick<CalendarEvent.OnTaskClicked> {
            featureRouter.navigate(AppRoute.Detail(it.task.id))
        }
        viewModelScope.launch { collectInitialState(date = today.date) }
    }

    private suspend fun collectInitialState(date: LocalDate) = runCatching {
        val week = getWeekUseCase(date).getOrThrow()
        val tasks = getTasksForDateUseCase(date).getOrNull() ?: emptyList()

        state = state.copy(
            uiState = UiState.Success,
            week = week,
            tasks = tasks,
            date = date,
            time = today.time,
        )
    }.onFailure { it.printStackTrace() }
}
