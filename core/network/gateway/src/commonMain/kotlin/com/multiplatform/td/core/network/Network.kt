package com.multiplatform.td.core.network

import com.multiplatform.td.core.environment.Environment
import com.multiplatform.td.core.injection.binding.ContributesBinder
import com.multiplatform.td.core.injection.scopes.NetworkScope
import de.jensklingenberg.ktorfit.Ktorfit

@ContributesBinder(
    scope = NetworkScope::class,
    boundType = Network::class,
)
class Network(
    val environment: Environment,
    val ktorfit: Ktorfit,
) {

    inline fun <reified T> create(
        serviceFactory: Ktorfit.() -> T,
        mockServiceFactory: () -> T,
    ): T = when {
        environment.isMock -> mockServiceFactory()
        else -> serviceFactory(ktorfit)
    }
}
