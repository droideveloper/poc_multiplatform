package com.multiplatform.todo.onboarding.repo

import com.multiplatform.todo.onboarding.Onboarding

interface OnboardingRepository {

    suspend fun save(onboarding: Onboarding): Result<Unit>
    suspend fun get(): Result<Onboarding>
}