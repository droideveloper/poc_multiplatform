package com.multiplatform.todo.tasks

data class Category(
    val id: Long = 0,
    val name: String,
    val description: String? = null,
    val color: CategoryColor,
    val iconRes: CategoryDrawableRes? = null,
)
