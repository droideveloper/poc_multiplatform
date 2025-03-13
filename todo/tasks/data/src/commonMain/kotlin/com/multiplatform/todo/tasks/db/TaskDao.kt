package com.multiplatform.todo.tasks.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert

@Dao
internal interface TaskDao {

    @Upsert
    suspend fun saveOrUpdate(task: TaskDto)

    @Query("SELECT * FROM tasks")
    suspend fun tasks(): List<TaskDto>

    @Query("SELECT * FROM tasks WHERE date(substr(dueDateTime, 1, 10)) = :date")
    suspend fun tasks(date: String): List<TaskDto>

    @Transaction
    @Query("SELECT * FROM tasks")
    suspend fun tasksWithSubTasks(): List<TaskWithSubTasksDto>

    @Transaction
    @Query("SELECT * FROM tasks WHERE id = :taskId")
    suspend fun task(taskId: Long): TaskDto?

    @Transaction
    @Query("SELECT * FROM tasks WHERE id = :taskId")
    suspend fun tasksWithSubTasks(taskId: Long): TaskWithSubTasksDto?

    @Transaction
    @Query("SELECT * FROM tasks WHERE date(substr(dueDateTime, 1, 10)) = :date")
    suspend fun tasksWithSubTaskForDate(date: String): List<TaskWithSubTasksDto>

    @Delete
    suspend fun delete(task: TaskDto)
}