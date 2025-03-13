package com.multiplatform.td.core.app

import com.multiplatform.td.core.app.inject.ComponentStore
import com.multiplatform.td.core.app.inject.ComponentStoreImpl
import com.multiplatform.td.core.injection.scopes.AppScope
import me.tatarka.inject.annotations.Provides

interface AppModule {

    val componentStore: ComponentStore

    @AppScope
    @Provides
    fun bindComponentStore(impl: ComponentStoreImpl): ComponentStore = impl
}
