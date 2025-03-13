package com.multiplatform.todo.onboarding.categories

internal sealed interface CategoriesEvent {

    data object OnScreenViewed : CategoriesEvent
}
