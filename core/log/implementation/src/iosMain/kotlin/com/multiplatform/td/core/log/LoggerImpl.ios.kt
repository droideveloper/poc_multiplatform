package com.multiplatform.td.core.log

import com.multiplatform.td.core.environment.Environment
import platform.Foundation.NSLog

internal class LoggerImpl(
    private val environment: Environment,
) : PlatformLogger {

    override fun log(level: LogLevel, tag: String, message: String) = when {
        environment.isDebug -> debugLogger(level, tag, message)
        else -> releaseLogger(level, tag, message)
    }

    private fun debugLogger(
        level: LogLevel, tag: String, message: String,
    ) {
        NSLog("[%s] [ %s ] : %s", level.asName(), tag, message)
    }

    private fun releaseLogger(
        level: LogLevel, tag: String, message: String,
    ) = Unit
    // may be want to log those in remote as non-fatal.

    private fun LogLevel.asName(): String = name.uppercase()
}
