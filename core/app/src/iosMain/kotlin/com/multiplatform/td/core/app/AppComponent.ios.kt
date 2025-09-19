package com.multiplatform.td.core.app

import com.multiplatform.td.core.coroutines.inject.CoroutinesModule
import com.multiplatform.td.core.environment.AppVersion
import com.multiplatform.td.core.environment.inject.EnvironmentModule
import com.multiplatform.td.core.injection.scopes.AppScope
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.KmpComponentCreate
import me.tatarka.inject.annotations.Provides
import platform.Foundation.NSBundle
import platform.Foundation.NSFileManager

@AppScope
@Component
actual abstract class AppComponent : AppModule, CoroutinesModule, EnvironmentModule  {
    actual companion object;

    val fileManager: NSFileManager
        @AppScope @Provides get() = NSFileManager.defaultManager

    actual val version: AppVersion
        @AppScope @Provides get() {
            val bundle = NSBundle.mainBundle
            return AppVersion(bundle.versionOrDefault())
        }
}

@KmpComponentCreate
expect fun createAppComponent(): AppComponent


internal const val VersionStringKey = "CFBundleShortVersionString"
internal const val VersionStringDefault = "1.0"
internal fun NSBundle.versionOrDefault(): String {
    val dictionary = requireNotNull(NSBundle.mainBundle.infoDictionary) {
        "dictionary of info is null"
    }
    return dictionary[VersionStringKey] as? String ?: VersionStringDefault
}
