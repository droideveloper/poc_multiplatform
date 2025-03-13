package com.multiiplatform.td.core.database.inject.binder

import com.multiiplatform.td.core.database.RoomDatabaseConfig
import com.multiiplatform.td.core.database.RoomDatabaseConfigImpl
import com.multiplatform.td.core.coroutines.DispatcherProvider
import com.multiplatform.td.core.injection.Binder
import me.tatarka.inject.annotations.Inject

@Inject
class RoomDatabaseConfigBinder(
    private val dispatcherProvider: DispatcherProvider,
) : Binder<RoomDatabaseConfig> {

    override fun invoke(): RoomDatabaseConfig =
        RoomDatabaseConfigImpl(dispatcherProvider)
}