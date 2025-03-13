package com.multiplatform.td.core.log.inject

import com.multiplatform.td.core.environment.inject.EnvironmentComponent
import com.multiplatform.td.core.injection.scopes.LoggerScope
import me.tatarka.inject.annotations.Component

@LoggerScope
@Component
abstract class LoggerComponent(
    @Component val environmentComponent: EnvironmentComponent,
) : LoggerModule {
    companion object;
}
