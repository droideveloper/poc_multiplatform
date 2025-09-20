package com.multiplatform.weather.city.inject.binder

import com.multiiplatform.td.core.database.DatabaseBuilderArgs
import com.multiiplatform.td.core.database.RoomDatabaseConfig
import com.multiiplatform.td.core.database.createDatabaseBuilder
import com.multiplatform.td.core.injection.Binder
import com.multiplatform.weather.city.db.CityDatabase
import me.tatarka.inject.annotations.Inject

@Inject
class CityDatabaseBinder(
    private val roomDatabaseConfig: RoomDatabaseConfig,
    private val databaseBuilderArgs: DatabaseBuilderArgs,
) : Binder<CityDatabase> {

    override fun invoke(): CityDatabase {
        val builder = createDatabaseBuilder<CityDatabase>(args = databaseBuilderArgs)
        return builder.apply(roomDatabaseConfig).build()
    }
}
