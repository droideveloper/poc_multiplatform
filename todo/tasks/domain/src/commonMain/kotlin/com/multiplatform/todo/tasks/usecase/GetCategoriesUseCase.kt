package com.multiplatform.todo.tasks.usecase

import com.multiplatform.todo.tasks.Category
import com.multiplatform.todo.tasks.repo.CategoryRepository
import me.tatarka.inject.annotations.Inject

@Inject
class GetCategoriesUseCase(
    private val repository: CategoryRepository,
) {

    suspend operator fun invoke(): Result<List<Category>> = runCatching {
        repository.categories()
            .getOrThrow()
    }
}