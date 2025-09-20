package com.multiplatform.todo.tasks.repo

import com.multiplatform.todo.tasks.Category
import com.multiplatform.todo.tasks.CategoryTasks
import com.multiplatform.todo.tasks.db.CategoryDao
import com.multiplatform.todo.tasks.error.CategoryException
import com.multiplatform.todo.tasks.mapper.toData
import com.multiplatform.todo.tasks.mapper.toDomain

internal class CategoryRepositoryImpl(
    private val dao: CategoryDao,
) : CategoryRepository {

    override suspend fun saveOrUpdate(category: Category): Result<Unit> = runCatching {
        dao.saveOrUpdate(category.toData())
    }

    override suspend fun categories(): Result<List<Category>> = runCatching {
        val result = dao.categories()
        when {
            result.isEmpty() -> throw CategoryException.EmptyData
            else ->
                result.map {
                    it.toDomain()
                }
        }
    }

    override suspend fun categoryTasks(): Result<List<CategoryTasks>> = runCatching {
        val result = dao.categoryTasks()
        when {
            result.isEmpty() -> throw CategoryException.EmptyData
            else ->
                result.map {
                    it.toDomain()
                }
        }
    }

    override suspend fun category(categoryId: Long): Result<Category> = runCatching {
        when (val result = dao.category(categoryId)) {
            null -> throw CategoryException.NotFound(categoryId)
            else -> result.toDomain()
        }
    }

    override suspend fun categoryTasks(categoryId: Long): Result<CategoryTasks> = runCatching {
        when (val result = dao.categoryTasks(categoryId)) {
            null -> throw CategoryException.NotFound(categoryId)
            else -> result.toDomain()
        }
    }

    override suspend fun delete(category: Category): Result<Unit> = runCatching {
        dao.delete(category.toData())
    }
}
