package com.multiplatform.todo.onboarding.mapper

import com.multiplatform.todo.onboarding.JsonOnboarding
import com.multiplatform.todo.onboarding.Onboarding

internal fun JsonOnboarding.toDomain(): Onboarding =
    Onboarding(
        areCategoriesSetUp = areCategoriesSetUp,
        areNotificationsSetUp = areCategoriesSetUp,
        isCompleted = isCompleted,
    )

internal fun Onboarding.toData(): JsonOnboarding =
    JsonOnboarding(
        areCategoriesSetUp = areCategoriesSetUp,
        areNotificationsSetUp = areNotificationsSetUp,
        isCompleted = isCompleted,
    )
