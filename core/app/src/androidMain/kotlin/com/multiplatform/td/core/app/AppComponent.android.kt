package com.multiplatform.td.core.app

import android.app.Application
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import com.multiplatform.td.core.coroutines.inject.CoroutinesModule
import com.multiplatform.td.core.environment.AppVersion
import com.multiplatform.td.core.environment.inject.EnvironmentModule
import com.multiplatform.td.core.injection.scopes.AppScope
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@AppScope
@Component
actual abstract class AppComponent(
    @get:AppScope @get:Provides val application: Application,
) : AppModule, CoroutinesModule, EnvironmentModule {
    actual companion object;

    abstract val context: Context

    actual val version: AppVersion
        @AppScope @Provides
        get() {
            val packageInfo = context.getPackageInfo()
            return AppVersion(packageInfo.versionOrDefault())
        }

    @AppScope
    @Provides
    fun bindContext(app: Application): Context = app
}

internal fun Context.getPackageInfo(): PackageInfo {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        packageManager.getPackageInfo(
            packageName,
            PackageManager.PackageInfoFlags.of(0),
        )
    } else {
        packageManager.getPackageInfo(packageName, 0)
    }
}

internal fun PackageInfo.versionOrDefault(): String {
    return versionName ?: "1.0"
}
