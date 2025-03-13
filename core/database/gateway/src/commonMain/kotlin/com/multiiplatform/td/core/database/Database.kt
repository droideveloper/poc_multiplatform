package com.multiiplatform.td.core.database

import com.multiplatform.td.core.environment.Environment
import me.tatarka.inject.annotations.Inject

@Inject
class Database(
    val environment: Environment,
) {

    inline fun <reified Dao> create(
        daoFactory: () -> Dao,
        mockDaoFactory: () -> Dao
    ): Dao = when {
        environment.isMock -> mockDaoFactory()
        else -> daoFactory()
    }
}