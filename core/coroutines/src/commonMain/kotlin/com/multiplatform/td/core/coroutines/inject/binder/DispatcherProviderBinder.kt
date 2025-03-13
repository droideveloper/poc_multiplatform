package com.multiplatform.td.core.coroutines.inject.binder

import com.multiplatform.td.core.coroutines.DispatcherProvider
import com.multiplatform.td.core.coroutines.DispatcherProviderImpl
import com.multiplatform.td.core.injection.Binder
import me.tatarka.inject.annotations.Inject

@Inject
class DispatcherProviderBinder : Binder<DispatcherProvider> {

    override fun invoke(): DispatcherProvider = DispatcherProviderImpl()
}