package com.multiplatform.td.core.environment.inject.binder

import com.multiplatform.td.core.environment.EnvironmentImpl
import com.multiplatform.td.core.injection.Binder
import me.tatarka.inject.annotations.Inject

@Inject
class EnvironmentBinder : Binder<EnvironmentAndInitializer> {

    override fun invoke(): EnvironmentAndInitializer {
        val impl = EnvironmentImpl()
        return EnvironmentAndInitializer(
            environment = impl,
            initializer = impl,
        )
    }
}