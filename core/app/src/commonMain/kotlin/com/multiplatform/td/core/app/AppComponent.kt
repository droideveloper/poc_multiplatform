package com.multiplatform.td.core.app

import com.multiplatform.td.core.coroutines.inject.CoroutinesModule
import com.multiplatform.td.core.environment.AppVersion
import com.multiplatform.td.core.environment.inject.EnvironmentModule
import com.multiplatform.td.core.injection.scopes.AppScope
import me.tatarka.inject.annotations.Component

@AppScope
@Component
expect abstract class AppComponent : AppModule, CoroutinesModule, EnvironmentModule {
    companion object;

    val version: AppVersion
}
