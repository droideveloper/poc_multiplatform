package com.multiplatform.todo.tasks.repo

import com.multiplatform.todo.tasks.Task
import com.multiplatform.todo.tasks.TaskSubTasks
import com.multiplatform.todo.tasks.db.CategoryDao
import com.multiplatform.todo.tasks.db.TaskDao
import com.multiplatform.todo.tasks.error.TaskException
import com.multiplatform.todo.tasks.mapper.toData
import com.multiplatform.todo.tasks.mapper.toDomain
import kotlinx.datetime.LocalDate

internal class TaskRepositoryImpl(
    private val dao: TaskDao,
    private val categoryDao: CategoryDao,
) : TaskRepository {

    override suspend fun saveOrUpdate(task: Task): Result<Unit> = runCatching {
        dao.saveOrUpdate(task.toData())
    }

    override suspend fun tasks(): Result<List<Task>> = runCatching {
        val categories = categoryDao.categories().map { it.toDomain() }
        val result = dao.tasks()
        when {
            result.isEmpty() -> throw TaskException.EmptyData
            else ->
                result.map {
                    it.toDomain(
                        category = categories.first { c -> c.id == it.categoryId },
                    )
                }
        }
    }

    override suspend fun tasks(date: LocalDate): Result<List<Task>> = runCatching {
        val dateString = LocalDate.Formats.ISO.format(date)
        val categories = categoryDao.categories().map { it.toDomain() }
        val result = dao.tasks(dateString)
        when {
            result.isEmpty() -> throw TaskException.EmptyData
            else ->
                result.map {
                    it.toDomain(
                        category = categories.first { c -> c.id == it.categoryId },
                    )
                }
        }
    }

    override suspend fun taskSubTasks(): Result<List<TaskSubTasks>> = runCatching {
        val categories = categoryDao.categories().map { it.toDomain() }
        val result = dao.tasksWithSubTasks()
        when {
            result.isEmpty() -> throw TaskException.EmptyData
            else ->
                result.map {
                    it.toDomain(
                        category = categories.first { c -> c.id == it.task.categoryId },
                    )
                }
        }
    }

    override suspend fun taskSubTasksForDate(date: LocalDate): Result<List<TaskSubTasks>> = runCatching {
        val dateString = LocalDate.Formats.ISO.format(date)
        val categories = categoryDao.categories().map { it.toDomain() }
        val result = dao.tasksWithSubTaskForDate(dateString)
        when {
            result.isEmpty() -> throw TaskException.EmptyDataForDate(dateString)
            else ->
                result.map {
                    it.toDomain(
                        category = categories.first { c -> c.id == it.task.categoryId },
                    )
                }
        }
    }

    override suspend fun task(taskId: Long): Result<Task> = runCatching {
        val categories = categoryDao.categories().map { it.toDomain() }
        when (val result = dao.task(taskId)) {
            null -> throw TaskException.NotFound(taskId)
            else -> result.toDomain(
                category = categories.first { c -> c.id == result.categoryId },
            )
        }
    }

    override suspend fun taskSubTasks(taskId: Long): Result<TaskSubTasks> = runCatching {
        val categories = categoryDao.categories().map { it.toDomain() }
        when (val result = dao.tasksWithSubTasks(taskId)) {
            null -> throw TaskException.NotFound(taskId)
            else -> result.toDomain(
                category = categories.first { c -> c.id == result.task.categoryId },
            )
        }
    }

    override suspend fun delete(task: Task): Result<Unit> = runCatching {
        dao.delete(task.toData())
    }
}
