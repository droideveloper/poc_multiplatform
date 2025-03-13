package com.multiplatform.todo.tasks.usecase

import com.multiplatform.todo.tasks.TaskSubTasks
import com.multiplatform.todo.tasks.repo.TaskRepository
import me.tatarka.inject.annotations.Inject

@Inject
class GetTaskWithSubTasksUseCase(
    private val repository: TaskRepository,
) {

    suspend operator fun invoke(taskId: Long): Result<TaskSubTasks> = runCatching {
        repository.taskSubTasks(taskId)
            .getOrThrow()
    }
}