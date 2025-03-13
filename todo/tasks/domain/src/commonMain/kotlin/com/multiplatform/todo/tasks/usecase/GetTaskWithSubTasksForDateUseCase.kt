package com.multiplatform.todo.tasks.usecase

import com.multiplatform.todo.tasks.TaskSubTasks
import com.multiplatform.todo.tasks.repo.TaskRepository
import kotlinx.datetime.LocalDate
import me.tatarka.inject.annotations.Inject

@Inject
class GetTaskWithSubTasksForDateUseCase(
    private val repository: TaskRepository,
) {

    suspend operator fun invoke(date: LocalDate): Result<List<TaskSubTasks>> = runCatching {
        repository.taskSubTasksForDate(date)
            .getOrThrow()
            .sortedBy { it.task.dueDateTime }
    }
}