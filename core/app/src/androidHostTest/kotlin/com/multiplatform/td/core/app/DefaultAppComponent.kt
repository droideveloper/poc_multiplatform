@file:OptIn(ExperimentalCoroutinesApi::class)

package com.multiplatform.td.core.app

import android.app.Application
import android.content.Context
import com.multiplatform.td.core.app.inject.ComponentStore
import com.multiplatform.td.core.app.inject.ComponentStoreImpl
import com.multiplatform.td.core.coroutines.DispatcherProvider
import com.multiplatform.td.core.environment.Environment
import com.multiplatform.td.core.environment.Initializer
import com.multiplatform.td.core.environment.OsVersion
import com.multiplatform.td.core.environment.Platform
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher

internal class DefaultAppComponent(application: Application) : AppComponent(application) {

    override val context: Context get() = application

    override val componentStore: ComponentStore get() = DefaultComponentStore

    override val dispatcherProvider: DispatcherProvider get() = DefaultDispatcherProvider

    override val environment: Environment get() = DefaultEnvironment

    override val environmentInitializer: Initializer<Environment> get() = DefaultEnvironment
}

internal object DefaultComponentStore : ComponentStore by ComponentStoreImpl()

internal object DefaultDispatcherProvider : DispatcherProvider {
    private val default: CoroutineDispatcher = UnconfinedTestDispatcher()

    override val ui: CoroutineDispatcher
        get() = default

    override val io: CoroutineDispatcher
        get() = default

    override val computation: CoroutineDispatcher
        get() = default
}

internal object DefaultEnvironment : Environment, Initializer<Environment> {

    override val isDebug: Boolean
        get() = true

    override val isRelease: Boolean
        get() = isDebug.not()

    override val osVersion: OsVersion
        get() = OsVersion.AndroidVersion(34)

    override val flavorName: String
        get() = "mock"

    override val platform: Platform
        get() = Platform.Android

    override fun invoke(args: Array<String>) = Unit
}
