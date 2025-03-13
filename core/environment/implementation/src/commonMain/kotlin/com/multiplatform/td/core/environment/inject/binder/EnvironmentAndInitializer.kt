package com.multiplatform.td.core.environment.inject.binder

import com.multiplatform.td.core.environment.Environment
import com.multiplatform.td.core.environment.Initializer

data class EnvironmentAndInitializer(
    val environment: Environment,
    val initializer: Initializer<Environment>,
)
