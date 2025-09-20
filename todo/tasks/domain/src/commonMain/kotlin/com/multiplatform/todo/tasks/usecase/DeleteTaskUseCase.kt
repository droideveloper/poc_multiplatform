package com.multiplatform.todo.tasks.usecase

import com.multiplatform.todo.tasks.Task
import com.multiplatform.todo.tasks.repo.TaskRepository
import me.tatarka.inject.annotations.Inject

@Inject
class DeleteTaskUseCase(
    private val repository: TaskRepository,
) {

    suspend operator fun invoke(task: Task): Result<Unit> = runCatching {
        repository.delete(task)
            .getOrThrow()
    }
}
