@file:OptIn(ExperimentalNativeApi::class)

package com.multiplatform.td.core.environment

import kotlin.experimental.ExperimentalNativeApi
import kotlin.native.Platform as KPlatform

internal actual val platformOsVersion: OsVersion
    get() = OsVersion.IosVersion(KPlatform.osFamily.name)

internal actual val platformPlatform: Platform
    get() = Platform.Ios
