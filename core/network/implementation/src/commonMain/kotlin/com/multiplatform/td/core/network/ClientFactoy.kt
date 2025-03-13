package com.multiplatform.td.core.network

import com.multiplatform.td.core.network.client.ClientConfigHttp
import com.multiplatform.td.core.network.client.HttpClientFactory

internal expect fun createClientFactory(config: ClientConfigHttp): HttpClientFactory