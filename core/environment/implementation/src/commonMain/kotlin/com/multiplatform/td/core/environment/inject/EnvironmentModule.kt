package com.multiplatform.td.core.environment.inject

import com.multiplatform.td.core.environment.Environment
import com.multiplatform.td.core.environment.GeneratedBinderModule
import com.multiplatform.td.core.environment.Initializer

interface EnvironmentModule : GeneratedBinderModule {

    val environment: Environment

    val environmentInitializer: Initializer<Environment>
}
