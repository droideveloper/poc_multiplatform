package com.multiplatform.td.core.log

import com.multiplatform.td.core.environment.Environment

internal actual fun createPlatformLogger(environment: Environment): PlatformLogger =
    LoggerImpl(environment)
