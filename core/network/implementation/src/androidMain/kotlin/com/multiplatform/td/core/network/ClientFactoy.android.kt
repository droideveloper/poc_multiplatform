package com.multiplatform.td.core.network

import com.multiplatform.td.core.network.client.ClientConfigHttp
import com.multiplatform.td.core.network.client.HttpClientFactory
import com.multiplatform.td.core.network.client.HttpClientFactoryImpl

internal actual fun createClientFactory(config: ClientConfigHttp): HttpClientFactory =
    HttpClientFactoryImpl(config)