package com.multiplatform.td.core.environment

internal class EnvironmentImpl : Environment, Initializer<Environment> {

    override val isDebug: Boolean
        get() = platformIsDebug

    override val isRelease: Boolean
        get() = isDebug.not()

    override val osVersion: OsVersion
        get() = platformOsVersion

    override val flavorName: String
        get() = BuildKonfig.KONFIG_FLAVOR

    override val platform: Platform
        get() = platformPlatform

    override fun invoke(args: Array<String>) = Unit
}
