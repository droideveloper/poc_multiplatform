package com.multiplatform.todo.tasks.inject.binder

import com.multiiplatform.td.core.database.Database
import com.multiplatform.td.core.injection.Binder
import com.multiplatform.todo.tasks.db.MockDao
import com.multiplatform.todo.tasks.db.TodoDatabase
import com.multiplatform.todo.tasks.repo.TaskRepository
import com.multiplatform.todo.tasks.repo.TaskRepositoryImpl
import me.tatarka.inject.annotations.Inject

@Inject
class TaskRepositoryBinder(
    private val db: TodoDatabase,
    private val database: Database,
) : Binder<TaskRepository> {

    override fun invoke(): TaskRepository =
        TaskRepositoryImpl(
            dao = database.create(
                daoFactory = { db.taskDao() },
                mockDaoFactory = { MockDao.taskDao },
            ),
            categoryDao = database.create(
                daoFactory = { db.categoryDao() },
                mockDaoFactory = { MockDao.categoryDao },
            ),
        )
}