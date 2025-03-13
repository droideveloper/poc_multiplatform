package com.multiplatform.todo.tasks.inject

import com.multiiplatform.td.core.database.Database
import com.multiplatform.td.core.environment.Environment
import com.multiplatform.td.core.injection.scopes.FeatureScope
import com.multiplatform.todo.tasks.db.TodoDatabase
import com.multiplatform.todo.tasks.inject.binder.CategoryRepositoryBinder
import com.multiplatform.todo.tasks.inject.binder.TaskRepositoryBinder
import com.multiplatform.todo.tasks.repo.CategoryRepository
import com.multiplatform.todo.tasks.repo.TaskRepository
import me.tatarka.inject.annotations.Provides

interface TasksDataModule {

    val db: TodoDatabase
    val environment: Environment
    val database: Database

    val categoryRepository: CategoryRepository
    val taskRepository: TaskRepository

    @FeatureScope
    @Provides
    fun bindCategoryRepository(binder: CategoryRepositoryBinder): CategoryRepository = binder()

    @FeatureScope
    @Provides
    fun bindTaskRepository(binder: TaskRepositoryBinder): TaskRepository = binder()
}
