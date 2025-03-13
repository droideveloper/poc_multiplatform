package com.multiplatform.td.core.coroutines.inject

import com.multiplatform.td.core.coroutines.DispatcherProvider
import com.multiplatform.td.core.coroutines.inject.binder.DispatcherProviderBinder
import com.multiplatform.td.core.injection.scopes.AppScope
import me.tatarka.inject.annotations.Provides

interface CoroutinesModule {

    val dispatcherProvider: DispatcherProvider

    @AppScope
    @Provides
    fun bindDispatcherProvider(provider: DispatcherProviderBinder): DispatcherProvider = provider()
}
