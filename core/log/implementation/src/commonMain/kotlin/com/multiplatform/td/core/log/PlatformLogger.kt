package com.multiplatform.td.core.log

import com.multiplatform.td.core.environment.Environment

internal expect fun createPlatformLogger(environment: Environment): PlatformLogger
