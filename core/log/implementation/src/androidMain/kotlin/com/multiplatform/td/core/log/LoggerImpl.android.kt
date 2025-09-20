package com.multiplatform.td.core.log

import android.util.Log
import com.multiplatform.td.core.environment.Environment

internal class LoggerImpl(
    private val environment: Environment,
) : PlatformLogger {

    override fun log(level: LogLevel, tag: String, message: String) = when {
        environment.isDebug -> debugLogger(level, tag, message)
        else -> releaseLogger(level, tag, message)
    }

    private fun debugLogger(
        level: LogLevel,
        tag: String,
        message: String,
    ) {
        val platformLog: (String?, String) -> Unit = when (level) {
            LogLevel.Debug -> Log::d
            LogLevel.Info -> Log::i
            LogLevel.Warn -> Log::w
            LogLevel.Error -> Log::e
        }
        platformLog(tag, message)
    }

    private fun releaseLogger(
        level: LogLevel,
        tag: String,
        message: String,
    ) = Unit
    // may be want to send error or warning levels to analytics like firebase as non-fatal.
}
