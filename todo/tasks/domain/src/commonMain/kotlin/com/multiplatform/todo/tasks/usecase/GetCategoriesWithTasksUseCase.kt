package com.multiplatform.todo.tasks.usecase

import com.multiplatform.todo.tasks.CategoryTasks
import com.multiplatform.todo.tasks.repo.CategoryRepository
import me.tatarka.inject.annotations.Inject

@Inject
class GetCategoriesWithTasksUseCase(
    private val repository: CategoryRepository,
) {

    suspend operator fun invoke(): Result<List<CategoryTasks>> = runCatching {
        repository.categoryTasks()
            .getOrThrow()
    }
}
