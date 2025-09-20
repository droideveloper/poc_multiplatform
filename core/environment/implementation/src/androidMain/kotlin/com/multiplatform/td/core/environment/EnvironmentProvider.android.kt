package com.multiplatform.td.core.environment

import android.os.Build

internal actual val platformOsVersion: OsVersion
    get() = OsVersion.AndroidVersion(Build.VERSION.SDK_INT)

internal actual val platformIsDebug: Boolean
    get() = BuildConfig.DEBUG

internal actual val platformPlatform: Platform
    get() = Platform.Android
