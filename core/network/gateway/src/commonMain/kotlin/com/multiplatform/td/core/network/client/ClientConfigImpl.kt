package com.multiplatform.td.core.network.client

import com.multiplatform.td.core.injection.binding.ContributesBinder
import com.multiplatform.td.core.injection.scopes.NetworkScope
import io.ktor.client.HttpClient

@ContributesBinder(scope = NetworkScope::class)
internal class ClientConfigImpl : ClientConfig {

    override fun invoke(client: HttpClient) {
        /*no-opt*/
    }
}