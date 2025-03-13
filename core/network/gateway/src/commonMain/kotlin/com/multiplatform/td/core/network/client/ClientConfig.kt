package com.multiplatform.td.core.network.client

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig

interface ClientConfig : (HttpClient) -> Unit
interface ClientConfigHttp : (HttpClientConfig<*>) -> Unit