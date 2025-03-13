package com.multiplatform.td.core.network.client

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.okhttp.OkHttp

internal class HttpClientFactoryImpl(
    private val config: (HttpClientConfig<*>) -> Unit,
) : HttpClientFactory {

    override operator fun invoke(): HttpClient = HttpClient(OkHttp) {
        config(this)
    }
}
