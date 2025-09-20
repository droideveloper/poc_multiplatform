package com.multiplatform.todo.tasks.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

@Dao
internal interface CategoryDao {

    @Upsert
    suspend fun saveOrUpdate(category: CategoryDto)

    @Query("SELECT * FROM categories")
    suspend fun categories(): List<CategoryDto>

    @Transaction
    @Query("SELECT * FROM categories")
    suspend fun categoryTasks(): List<CategoryWithTasksDto>

    @Query("SELECT * FROM categories WHERE id = :categoryId")
    suspend fun category(categoryId: Long): CategoryDto?

    @Transaction
    @Query("SELECT * FROM categories WHERE id = :categoryId")
    suspend fun categoryTasks(categoryId: Long): CategoryWithTasksDto?

    @Delete
    suspend fun delete(category: CategoryDto)
}

internal object MockDao {

    internal suspend inline fun <reified T> withDispatcher(
        crossinline producer: suspend () -> T,
    ): T = withContext(Dispatchers.IO) {
        producer()
    }

    val categoryDao: CategoryDao get() =
        object : CategoryDao {

            private val categories = mutableListOf<CategoryDto>()

            init {
                categories.addAll(MockDao.categories)
            }

            override suspend fun saveOrUpdate(category: CategoryDto) = withDispatcher<Unit> {
                val index = categories.indexOfFirst { it.id == category.id }
                when {
                    index == -1 -> categories.add(category)
                    else -> categories[index] = category
                }
            }

            override suspend fun categories(): List<CategoryDto> = withDispatcher { categories }

            override suspend fun categoryTasks(): List<CategoryWithTasksDto> = withDispatcher {
                categories.map { category ->
                    val tasks = taskDao.tasks().filter { task -> task.categoryId == category.id }
                    CategoryWithTasksDto(
                        category = category,
                        tasks = tasks,
                    )
                }
            }

            override suspend fun categoryTasks(categoryId: Long): CategoryWithTasksDto? = withDispatcher {
                val category = categories.firstOrNull { it.id == categoryId }
                when (category) {
                    null -> null
                    else -> CategoryWithTasksDto(
                        category = category,
                        tasks = taskDao.tasks().filter { task -> task.categoryId == categoryId },
                    )
                }
            }

            override suspend fun category(categoryId: Long): CategoryDto? = withDispatcher {
                categories.firstOrNull { it.id == categoryId }
            }

            override suspend fun delete(category: CategoryDto) = withDispatcher<Unit> {
                val index = categories.indexOfFirst { it.id == category.id }
                when {
                    index == -1 -> Unit
                    else -> categories.remove(category)
                }
            }
        }

    val taskDao: TaskDao get() =
        object : TaskDao {

            private val tasks = mutableListOf<TaskDto>()

            init {
                tasks.addAll(MockDao.tasks)
            }

            override suspend fun saveOrUpdate(task: TaskDto) = withDispatcher<Unit> {
                val index = tasks.indexOfFirst { it.id == task.id }
                when {
                    index == -1 -> tasks.add(task)
                    else -> tasks[index] = task
                }
            }

            override suspend fun tasks(): List<TaskDto> = withDispatcher { tasks }

            override suspend fun tasks(date: String): List<TaskDto> = withDispatcher {
                tasks.filter { task -> task.dueDateTime.startsWith(date) }
            }

            override suspend fun tasksWithSubTasks(): List<TaskWithSubTasksDto> = withDispatcher {
                tasks
                    .filter { it.taskId == null }
                    .map { task ->
                        TaskWithSubTasksDto(
                            task = task,
                            tasks = tasks.filter { it.taskId == task.id },
                        )
                    }
            }

            override suspend fun tasksWithSubTasks(taskId: Long): TaskWithSubTasksDto? = withDispatcher {
                val task = tasks.firstOrNull { it.id == taskId && it.taskId == null }
                when (task) {
                    null -> null
                    else -> TaskWithSubTasksDto(
                        task = task,
                        tasks = tasks.filter { t -> t.taskId == task.id },
                    )
                }
            }

            override suspend fun task(taskId: Long): TaskDto? = withDispatcher {
                tasks.firstOrNull { it.id == taskId }
            }

            override suspend fun tasksWithSubTaskForDate(date: String): List<TaskWithSubTasksDto> = withDispatcher {
                val tasksForDate = tasks.filter { task -> task.dueDateTime.startsWith(date) && task.taskId == null }
                tasksForDate.map { task ->
                    TaskWithSubTasksDto(
                        task = task,
                        tasks = tasks.filter { it.taskId == task.id },
                    )
                }
            }

            override suspend fun delete(task: TaskDto) = withDispatcher<Unit> {
                val index = tasks.indexOfFirst { it.id == task.id }
                when {
                    index == -1 -> Unit
                    else -> tasks.remove(task)
                }
            }
        }

    private val categories = listOf(
        CategoryDto(
            id = 1,
            name = "Dashboard",
            description = "Dashboard related task will be help inside this category",
            color = -6440513913749504,
            iconRes = null, // may be test this later
        ),
        CategoryDto(
            id = 2,
            name = "Shopping",
            description = "Sopping list stored in this category",
            color = -62602949385256960,
            iconRes = null, // may be test this later
        ),
        CategoryDto(
            id = 3,
            name = "Knowledge",
            description = "Building up knowledge and put them in here",
            color = -69239167778816,
            iconRes = null, // may be test this later
        ),
        CategoryDto(
            id = 4,
            name = "Health",
            description = "Health tracking and improving it",
            color = -114349209288704,
            iconRes = null, // may be test this later
        ),
        CategoryDto(
            id = 5,
            name = "Work",
            description = "Work related task will be held in here",
            color = -71892083178209280,
            iconRes = null, // may be test this later
        ),
    )

    private val tasks = listOf(
        TaskDto(
            id = 1,
            categoryId = 1,
            title = "Redesign the admin dashboard",
            description = "UI/X improvement of admin dashboard epic",
            dueDateTime = "2025-03-04T14:00:00",
            duration = 45 * 1000 * 60L,
            status = "progress",
            taskId = null,
        ),
        TaskDto(
            id = 2,
            categoryId = 1,
            title = "Change color palette of dashboard",
            description = "Prepare detailed color palettes with reason behind it",
            dueDateTime = "2025-03-04T17:00:00",
            duration = 45 * 1000 * 60L,
            status = "progress",
            taskId = 1,
        ),
        TaskDto(
            id = 3,
            categoryId = 1,
            title = "Change icons of dashboard",
            description = "Prepare detailed icon options with reason behind it",
            dueDateTime = "2025-03-05T17:00:00",
            duration = 45 * 1000 * 60L,
            status = "progress",
            taskId = 1,
        ),
        TaskDto(
            id = 4,
            categoryId = 1,
            title = "Change components of dashboard",
            description = "Prepare detailed component options with reason behind it",
            dueDateTime = "2025-03-06T17:00:00",
            duration = 45 * 1000 * 60L,
            status = "progress",
            taskId = 1,
        ),
        TaskDto(
            id = 5,
            categoryId = 2,
            title = "Shopping for groceries",
            description = "Eggs, milk, apple and bread.",
            dueDateTime = "2025-03-04T19:00:00",
            duration = 45 * 1000 * 60L,
            status = "progress",
            taskId = null,
        ),
        TaskDto(
            id = 6,
            categoryId = 3,
            title = "E-Learning class on English",
            description = "Prepare beforehand for class next day",
            dueDateTime = "2025-03-04T21:00:00",
            duration = 45 * 1000 * 60L,
            status = "progress",
            taskId = null,
        ),
        TaskDto(
            id = 7,
            categoryId = 4,
            title = "Doctor appointment",
            description = "Get blood test result before hand",
            dueDateTime = "2025-03-04T10:00:00",
            duration = 45 * 1000 * 60L,
            status = "progress",
            taskId = null,
        ),
        TaskDto(
            id = 8,
            categoryId = 3,
            title = "Pre-check content of tomorrow class",
            description = "Prepare beforehand for class next day",
            dueDateTime = "2025-03-04T21:00:00",
            duration = 45 * 1000 * 60L,
            status = "progress",
            taskId = 6,
        ),
        TaskDto(
            id = 9,
            categoryId = 3,
            title = "Pre-Check content of yesterday class",
            description = "Prepare beforehand for class next day",
            dueDateTime = "2025-03-04T21:00:00",
            duration = 45 * 1000 * 60L,
            status = "progress",
            taskId = 6,
        ),
        TaskDto(
            id = 10,
            categoryId = 3,
            title = "Be prepared for assignments given for tomorrow",
            description = "Prepare beforehand for class next day",
            dueDateTime = "2025-03-04T21:00:00",
            duration = 45 * 1000 * 60L,
            status = "progress",
            taskId = 6,
        ),
    )
}
