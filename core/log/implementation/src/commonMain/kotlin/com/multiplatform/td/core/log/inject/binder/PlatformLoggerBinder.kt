package com.multiplatform.td.core.log.inject.binder

import com.multiplatform.td.core.environment.Environment
import com.multiplatform.td.core.injection.Binder
import com.multiplatform.td.core.log.PlatformLogger
import com.multiplatform.td.core.log.createPlatformLogger
import me.tatarka.inject.annotations.Inject

@Inject
class PlatformLoggerBinder(
    private val environment: Environment,
) : Binder<PlatformLogger> {

    override fun invoke(): PlatformLogger =
        createPlatformLogger(environment)
}