package com.multiplatform.td.core.network.client

import io.ktor.client.HttpClient

interface HttpClientFactory {

    operator fun invoke(): HttpClient
}
