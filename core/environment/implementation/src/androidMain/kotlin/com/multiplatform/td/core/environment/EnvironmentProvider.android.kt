package com.multiplatform.td.core.environment

import android.os.Build

internal actual val platformOsVersion: OsVersion
    get() = OsVersion.AndroidVersion(Build.VERSION.SDK_INT)

internal actual val platformIsDebug: Boolean
    get() = false // BuildConfig.DEBUG find another way to get it in here

internal actual val platformPlatform: Platform
    get() = Platform.Android
