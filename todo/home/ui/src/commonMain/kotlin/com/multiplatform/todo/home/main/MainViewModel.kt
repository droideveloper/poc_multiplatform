package com.multiplatform.todo.home.main

import androidx.lifecycle.viewModelScope
import com.multiplatform.td.core.injection.binding.ContributesViewModel
import com.multiplatform.td.core.injection.scopes.FeatureScope
import com.multiplatform.td.core.mvi.MviViewModel
import com.multiplatform.td.core.navigation.FeatureRouter
import com.multiplatform.todo.tasks.AppRoute
import com.multiplatform.todo.tasks.usecase.GetTaskWithSubTasksForDateUseCase
import com.multiplatform.todo.tasks.usecase.GetTasksForDateUseCase
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import me.tatarka.inject.annotations.Assisted

@ContributesViewModel(scope = FeatureScope::class)
internal class MainViewModel(
    private val getTasksForDateUseCase: GetTasksForDateUseCase,
    private val getTaskWithSubTasksForDateUseCase: GetTaskWithSubTasksForDateUseCase,
    @Assisted private val featureRouter: FeatureRouter,
) : MviViewModel<MainEvent, MainState>(
    initialState = MainState(),
) {

    private val today by lazy {
        LocalDateTime.Formats.ISO.parse("2025-03-04T12:00:00")
    }

    init {
        on<MainEvent.OnScreenViewed> {
            // TODO log for analytics or whatever
        }
        onClick<MainEvent.OnTaskClicked> {
            featureRouter.navigate(AppRoute.Detail(it.task.id))
        }
        viewModelScope.launch { collectInitialState() }
    }

    private suspend fun collectInitialState() = runCatching {
        val todayScheduleTasks = getTasksForDateUseCase(today.date).getOrThrow()
        val todayTasks = getTaskWithSubTasksForDateUseCase(today.date).getOrThrow()
        // update state
        state = state.copy(
            uiState = UiState.Success,
            todayTasks = todayTasks,
            todayScheduleTasks = todayScheduleTasks,
            date = today.date,
            time = today.time,
        )
    }.onFailure { it.printStackTrace() }
}
