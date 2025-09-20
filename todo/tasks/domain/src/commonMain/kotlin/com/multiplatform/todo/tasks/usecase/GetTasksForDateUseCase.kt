package com.multiplatform.todo.tasks.usecase

import com.multiplatform.todo.tasks.Task
import com.multiplatform.todo.tasks.repo.TaskRepository
import kotlinx.datetime.LocalDate
import me.tatarka.inject.annotations.Inject

@Inject
class GetTasksForDateUseCase(
    private val repository: TaskRepository,
) {

    suspend operator fun invoke(date: LocalDate): Result<List<Task>> = runCatching {
        repository.tasks(date)
            .getOrThrow()
            .sortedBy { it.dueDateTime }
    }
}
