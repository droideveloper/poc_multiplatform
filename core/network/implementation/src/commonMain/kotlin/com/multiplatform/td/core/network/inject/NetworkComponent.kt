package com.multiplatform.td.core.network.inject

import com.multiplatform.td.core.injection.scopes.NetworkScope
import com.multiplatform.td.core.log.inject.LoggerComponent
import com.multiplatform.td.core.network.Url
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@NetworkScope
@Component
abstract class NetworkComponent(
    @Component val loggerComponent: LoggerComponent,
    @get:NetworkScope @get:Provides val url: Url,
) : NetworkModule {
    companion object;
}
