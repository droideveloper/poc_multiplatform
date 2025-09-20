package com.multiplatform.todo.tasks.task

import androidx.lifecycle.viewModelScope
import com.multiplatform.td.core.injection.binding.ContributesViewModel
import com.multiplatform.td.core.injection.scopes.FeatureScope
import com.multiplatform.td.core.mvi.MviViewModel
import com.multiplatform.td.core.navigation.FeatureRouter
import com.multiplatform.todo.tasks.AppRoute
import com.multiplatform.todo.tasks.usecase.GetTasksUseCase
import kotlinx.coroutines.launch

@ContributesViewModel(scope = FeatureScope::class)
internal class TasksViewModel(
    private val featureRouter: FeatureRouter,
    private val getTasksUseCase: GetTasksUseCase,
) : MviViewModel<TasksEvent, TasksState>(
    initialState = TasksState(),
) {

    init {
        on<TasksEvent.OnScreenViewed> {
            // TODO log view or send to analytics
        }
        onClick<TasksEvent.OnTaskClicked> {
            featureRouter.navigate(AppRoute.Detail(it.task.id))
        }
        viewModelScope.launch { collectInitialState() }
    }

    private suspend fun collectInitialState() = runCatching {
        val tasks = getTasksUseCase()
            .getOrThrow()
            .sortedBy { it.dueDateTime }

        state = state.copy(
            uiState = UiState.Success,
            tasks = tasks,
        )
    }
}
