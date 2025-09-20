package com.multiiplatform.td.core.database.inject.binder

import com.multiiplatform.td.core.database.DatabaseBuilderArgsFactory
import com.multiplatform.td.core.injection.Binder

expect class DatabaseBuilderArgFactoryBinder : Binder<DatabaseBuilderArgsFactory> {
    override fun invoke(): DatabaseBuilderArgsFactory
}
