package com.multiplatform.todo.tasks.error

sealed class CategoryException(
    override val message: String?,
) : IllegalArgumentException(message) {

    data class NotFound(
        val categoryId: Long,
    ): CategoryException(
        message = "category with id $categoryId does not exists.",
    )

    data object EmptyData : CategoryException(
        message = "categories are empty, does not exists.",
    )
}