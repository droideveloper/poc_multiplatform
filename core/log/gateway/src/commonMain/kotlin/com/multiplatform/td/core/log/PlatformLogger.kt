package com.multiplatform.td.core.log

interface PlatformLogger {

    fun log(
        level: LogLevel = LogLevel.Info,
        tag: String,
        message: String,
    )
}
