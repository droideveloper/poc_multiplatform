package com.multiplatform.todo.tasks.inject.binder

import com.multiiplatform.td.core.database.Database
import com.multiplatform.td.core.injection.Binder
import com.multiplatform.todo.tasks.db.MockDao
import com.multiplatform.todo.tasks.db.TodoDatabase
import com.multiplatform.todo.tasks.repo.CategoryRepository
import com.multiplatform.todo.tasks.repo.CategoryRepositoryImpl
import me.tatarka.inject.annotations.Inject

@Inject
class CategoryRepositoryBinder(
    private val db: TodoDatabase,
    private val database: Database,
) : Binder<CategoryRepository> {

    override fun invoke(): CategoryRepository =
        CategoryRepositoryImpl(
            dao = database.create(
                daoFactory = { db.categoryDao() },
                mockDaoFactory = { MockDao.categoryDao },
            ),
        )
}