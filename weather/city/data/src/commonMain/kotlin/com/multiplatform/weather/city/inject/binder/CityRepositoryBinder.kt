package com.multiplatform.weather.city.inject.binder

import com.multiiplatform.td.core.database.Database
import com.multiplatform.td.core.injection.Binder
import com.multiplatform.weather.city.db.CityDatabase
import com.multiplatform.weather.city.db.MockDao
import com.multiplatform.weather.city.repo.CityRepository
import com.multiplatform.weather.city.repo.CityRepositoryImpl
import me.tatarka.inject.annotations.Inject

@Inject
class CityRepositoryBinder(
    private val db: CityDatabase,
    private val database: Database,
) : Binder<CityRepository> {

    override fun invoke(): CityRepository =
        CityRepositoryImpl(
            dao = database.create(
                daoFactory = { db.cityDao() },
                mockDaoFactory = { MockDao.create() },
            ),
        )
}
