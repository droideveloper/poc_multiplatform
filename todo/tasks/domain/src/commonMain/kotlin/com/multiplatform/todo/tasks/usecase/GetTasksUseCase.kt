package com.multiplatform.todo.tasks.usecase

import com.multiplatform.todo.tasks.Task
import com.multiplatform.todo.tasks.repo.TaskRepository
import me.tatarka.inject.annotations.Inject

@Inject
class GetTasksUseCase(
    private val repository: TaskRepository,
) {

    suspend operator fun invoke(): Result<List<Task>> = runCatching {
        repository.tasks()
            .getOrThrow()
    }
}
