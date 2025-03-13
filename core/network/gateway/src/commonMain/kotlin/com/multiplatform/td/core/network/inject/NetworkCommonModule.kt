package com.multiplatform.td.core.network.inject

import com.multiplatform.td.core.injection.scopes.NetworkScope
import com.multiplatform.td.core.network.GeneratedBinderModule
import com.multiplatform.td.core.network.KtorfitBuilderFactory
import com.multiplatform.td.core.network.client.ClientConfig
import com.multiplatform.td.core.network.inject.binder.KtorfitBinder
import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.converter.Converter
import me.tatarka.inject.annotations.Provides

interface NetworkCommonModule : GeneratedBinderModule {

    val clientConfig: ClientConfig
    val converterFactory: Converter.Factory

    @NetworkScope
    @Provides
    fun ktorfitBuilderFactory(factory: KtorfitBuilderFactory): Ktorfit.Builder = factory()

    @NetworkScope
    @Provides
    fun bindKtorfit(binder: KtorfitBinder): Ktorfit = binder()
}
