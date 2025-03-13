package com.multiplatform.todo.tasks.usecase

import com.multiplatform.todo.tasks.Category
import com.multiplatform.todo.tasks.repo.CategoryRepository
import me.tatarka.inject.annotations.Inject

@Inject
class DeleteCategoryUseCase(
    private val repository: CategoryRepository,
) {

    suspend operator fun invoke(category: Category): Result<Unit> = runCatching {
        repository.delete(category)
            .getOrThrow()
    }
}