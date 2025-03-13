package com.multiplatform.todo.tasks.repo

import com.multiplatform.todo.tasks.Task
import com.multiplatform.todo.tasks.TaskSubTasks
import kotlinx.datetime.LocalDate

interface TaskRepository {

    suspend fun saveOrUpdate(task: Task): Result<Unit>
    suspend fun tasks(): Result<List<Task>>
    suspend fun tasks(date: LocalDate): Result<List<Task>>
    suspend fun task(taskId: Long): Result<Task>
    suspend fun taskSubTasks(): Result<List<TaskSubTasks>>
    suspend fun taskSubTasksForDate(date: LocalDate): Result<List<TaskSubTasks>>
    suspend fun taskSubTasks(taskId: Long): Result<TaskSubTasks>
    suspend fun delete(task: Task): Result<Unit>
}