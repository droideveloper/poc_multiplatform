package com.multiplatform.td.core.datastore

import com.multiplatform.td.core.coroutines.DispatcherProvider
import com.multiplatform.td.core.injection.binding.ContributesBinder
import com.multiplatform.td.core.injection.scopes.DataStoreScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@ContributesBinder(scope = DataStoreScope::class)
internal class CoroutinesScopeFactoryImpl(
    private val dispatcherProvider: DispatcherProvider,
) : CoroutinesScopeFactory {

    override fun invoke(): CoroutineScope =
        CoroutineScope(dispatcherProvider.io + SupervisorJob())
}
