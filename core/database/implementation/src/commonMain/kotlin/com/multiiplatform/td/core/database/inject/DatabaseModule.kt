package com.multiiplatform.td.core.database.inject

import com.multiiplatform.td.core.database.DatabaseBuilderArgs
import com.multiiplatform.td.core.database.DatabaseBuilderArgsFactory
import com.multiiplatform.td.core.database.RoomDatabaseConfig
import com.multiiplatform.td.core.database.inject.binder.DatabaseBuilderArgFactoryBinder
import com.multiiplatform.td.core.database.inject.binder.RoomDatabaseConfigBinder
import com.multiplatform.td.core.injection.scopes.DatabaseScope
import me.tatarka.inject.annotations.Provides

interface DatabaseModule {

    @DatabaseScope
    @Provides
    fun databaseBuilderArgsFactory(factory: DatabaseBuilderArgsFactory): DatabaseBuilderArgs = factory()

    @DatabaseScope
    @Provides
    fun bindDatabaseBuilderArgFactory(binder: DatabaseBuilderArgFactoryBinder): DatabaseBuilderArgsFactory = binder()

    @DatabaseScope
    @Provides
    fun bindRoomDatabaseConfig(binder: RoomDatabaseConfigBinder): RoomDatabaseConfig = binder()
}
