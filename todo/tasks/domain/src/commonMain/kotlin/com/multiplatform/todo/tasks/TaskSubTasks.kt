package com.multiplatform.todo.tasks

data class TaskSubTasks(
    val task: Task,
    val tasks: List<Task>,
)