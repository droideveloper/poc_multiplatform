package com.multiplatform.td.core.network.inject.binder

import com.multiplatform.td.core.injection.Binder
import com.multiplatform.td.core.network.client.ClientConfigHttp
import com.multiplatform.td.core.network.client.HttpClientFactory
import com.multiplatform.td.core.network.createClientFactory
import me.tatarka.inject.annotations.Inject

@Inject
class HttpClientFactoryBinder(
    private val config: ClientConfigHttp,
) : Binder<HttpClientFactory> {

    override fun invoke(): HttpClientFactory =
        createClientFactory(config)
}
