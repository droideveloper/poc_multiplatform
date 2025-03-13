package com.multiplatform.weather.onboarding.repo

interface OnboardingRepository {

    suspend fun save(isCompleted: Boolean): Result<Unit>
    suspend fun get(): Result<Boolean>
}