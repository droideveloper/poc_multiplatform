package com.multiplatform.td.core.environment.inject

import com.multiplatform.td.core.injection.scopes.AppScope
import me.tatarka.inject.annotations.Component

@AppScope
@Component
abstract class EnvironmentComponent : EnvironmentModule {
    companion object;
}