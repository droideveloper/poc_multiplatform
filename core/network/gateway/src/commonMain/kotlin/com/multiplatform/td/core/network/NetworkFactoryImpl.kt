package com.multiplatform.td.core.network

import com.multiplatform.td.core.environment.Environment
import com.multiplatform.td.core.injection.binding.ContributesBinder
import com.multiplatform.td.core.injection.scopes.NetworkScope
import de.jensklingenberg.ktorfit.Ktorfit

@ContributesBinder(scope = NetworkScope::class)
internal class NetworkFactoryImpl(
    private val environment: Environment,
    private val ktorfit: Ktorfit,
) : NetworkFactory {

    override fun create(): Network =
        Network(
            environment = environment,
            ktorfit = ktorfit,
        )
}