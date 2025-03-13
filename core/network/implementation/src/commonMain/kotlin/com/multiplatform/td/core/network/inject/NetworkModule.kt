package com.multiplatform.td.core.network.inject

import com.multiplatform.td.core.injection.scopes.NetworkScope
import com.multiplatform.td.core.network.client.HttpClientFactory
import com.multiplatform.td.core.network.inject.binder.HttpClientFactoryBinder
import io.ktor.client.HttpClient
import me.tatarka.inject.annotations.Provides

interface NetworkModule : NetworkCommonModule {

    val client: HttpClient
    val httpClientFactory: HttpClientFactory

    @NetworkScope
    @Provides
    fun httpClientFactory(factory: HttpClientFactory): HttpClient = factory()

    @NetworkScope
    @Provides
    fun bindHttpClientFactory(binder: HttpClientFactoryBinder): HttpClientFactory = binder()
}
