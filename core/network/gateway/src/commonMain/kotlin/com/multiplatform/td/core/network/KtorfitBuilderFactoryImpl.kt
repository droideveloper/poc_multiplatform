package com.multiplatform.td.core.network

import com.multiplatform.td.core.injection.binding.ContributesBinder
import com.multiplatform.td.core.injection.scopes.NetworkScope
import com.multiplatform.td.core.network.client.ClientConfig
import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.converter.Converter
import io.ktor.client.HttpClient

@ContributesBinder(scope = NetworkScope::class)
class KtorfitBuilderFactoryImpl(
    private val httpClient: HttpClient,
    private val clientConfig: ClientConfig,
    private val converterFactory: Converter.Factory,
) : KtorfitBuilderFactory {

    override fun invoke(): Ktorfit.Builder = Ktorfit.Builder().apply {
        httpClient(
            this@KtorfitBuilderFactoryImpl.httpClient
                .apply(clientConfig)
        )
        converterFactories(converterFactory)
    }
}