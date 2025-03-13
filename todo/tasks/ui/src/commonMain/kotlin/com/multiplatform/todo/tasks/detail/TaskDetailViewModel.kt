package com.multiplatform.todo.tasks.detail

import androidx.lifecycle.viewModelScope
import com.multiplatform.td.core.injection.binding.ContributesViewModel
import com.multiplatform.td.core.injection.scopes.FeatureScope
import com.multiplatform.td.core.mvi.MviViewModel
import com.multiplatform.td.core.navigation.FeatureRouter
import com.multiplatform.todo.tasks.usecase.GetTasksUseCase
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted

@ContributesViewModel(scope = FeatureScope::class)
internal class TaskDetailViewModel(
    private val featureRouter: FeatureRouter,
    private val getTasksUseCase: GetTasksUseCase,
    @Assisted private val taskId: Long,
) : MviViewModel<TaskDetailEvent, TaskDetailState>(
    initialState = TaskDetailState()
) {

    init {
        on<TaskDetailEvent.OnScreenViewed> {
            // TODO implement logging or analytics
        }
        onClick<TaskDetailEvent.OnBackClicked> {
            featureRouter.back()
        }
        viewModelScope.launch { collectInitialState() }
    }

    private suspend fun collectInitialState() = runCatching {
        val task = getTasksUseCase().getOrThrow()
            .firstOrNull { it.id == taskId }
            ?: throw IllegalArgumentException("task not found $taskId")

        state = state.copy(
            uiState = UiState.Success(task)
        )
    }
}