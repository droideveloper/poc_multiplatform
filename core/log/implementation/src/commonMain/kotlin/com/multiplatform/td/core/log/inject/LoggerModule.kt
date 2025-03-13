package com.multiplatform.td.core.log.inject

import com.multiplatform.td.core.injection.scopes.LoggerScope
import com.multiplatform.td.core.log.PlatformLogger
import com.multiplatform.td.core.log.inject.binder.PlatformLoggerBinder
import me.tatarka.inject.annotations.Provides

interface LoggerModule {

    val platformLogger: PlatformLogger

    @LoggerScope
    @Provides
    fun bindPlatformLogger(binder: PlatformLoggerBinder): PlatformLogger = binder()
}
