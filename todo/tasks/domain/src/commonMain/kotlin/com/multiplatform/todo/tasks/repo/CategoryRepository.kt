package com.multiplatform.todo.tasks.repo

import com.multiplatform.todo.tasks.Category
import com.multiplatform.todo.tasks.CategoryTasks

interface CategoryRepository {

    suspend fun saveOrUpdate(category: Category): Result<Unit>
    suspend fun categories(): Result<List<Category>>
    suspend fun category(categoryId: Long): Result<Category>
    suspend fun categoryTasks(): Result<List<CategoryTasks>>
    suspend fun categoryTasks(categoryId: Long): Result<CategoryTasks>
    suspend fun delete(category: Category): Result<Unit>
}
