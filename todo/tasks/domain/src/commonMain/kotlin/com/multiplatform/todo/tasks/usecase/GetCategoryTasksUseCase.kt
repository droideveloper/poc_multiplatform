package com.multiplatform.todo.tasks.usecase

import com.multiplatform.todo.tasks.CategoryTasks
import com.multiplatform.todo.tasks.repo.CategoryRepository
import me.tatarka.inject.annotations.Inject

@Inject
class GetCategoryTasksUseCase(
    private val repository: CategoryRepository,
) {

    suspend operator fun invoke(categoryId: Long): Result<CategoryTasks> = runCatching {
        repository.categoryTasks(categoryId)
            .getOrThrow()
    }
}