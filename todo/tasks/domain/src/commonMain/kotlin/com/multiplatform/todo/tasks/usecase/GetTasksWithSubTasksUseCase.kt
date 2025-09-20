package com.multiplatform.todo.tasks.usecase

import com.multiplatform.todo.tasks.TaskSubTasks
import com.multiplatform.todo.tasks.repo.TaskRepository
import me.tatarka.inject.annotations.Inject

@Inject
class GetTasksWithSubTasksUseCase(
    private val repository: TaskRepository,
) {
    suspend operator fun invoke(): Result<List<TaskSubTasks>> = runCatching {
        repository.taskSubTasks()
            .getOrThrow()
    }
}
