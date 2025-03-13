package com.multiplatform.td.core.environment.inject

import com.multiplatform.td.core.environment.Environment
import com.multiplatform.td.core.environment.Initializer
import com.multiplatform.td.core.environment.inject.binder.EnvironmentBinder
import com.multiplatform.td.core.injection.scopes.AppScope
import me.tatarka.inject.annotations.Provides

interface EnvironmentModule {

    val environment: Environment
    val environmentInitializer: Initializer<Environment>

    @AppScope
    @Provides
    fun bindEnvironment(binder: EnvironmentBinder): Environment = binder().environment

    @AppScope
    @Provides
    fun bindEnvironmentInitializer(binder: EnvironmentBinder): Initializer<Environment> = binder().initializer
}
