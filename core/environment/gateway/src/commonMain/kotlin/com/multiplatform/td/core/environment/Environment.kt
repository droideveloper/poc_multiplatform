package com.multiplatform.td.core.environment

interface Environment {

    val flavorName: String

    val platform: Platform

    val isDebug: Boolean

    val isRelease: Boolean

    val osVersion: OsVersion

    val isMock: Boolean
        get() = flavorName == "mock"

    val isStaging: Boolean
        get() = flavorName == "staging"

    val isProd: Boolean
        get() = flavorName == "prod"
}
