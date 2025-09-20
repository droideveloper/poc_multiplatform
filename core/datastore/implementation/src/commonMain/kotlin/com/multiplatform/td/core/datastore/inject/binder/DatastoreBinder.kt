package com.multiplatform.td.core.datastore.inject.binder

import com.multiplatform.td.core.datastore.DataStorePreferencesFactory
import com.multiplatform.td.core.datastore.Datastore
import com.multiplatform.td.core.environment.Environment
import com.multiplatform.td.core.injection.Binder
import me.tatarka.inject.annotations.Inject

@Inject
class DatastoreBinder(
    private val environment: Environment,
    private val dataStorePreferencesFactory: DataStorePreferencesFactory,
) : Binder<Datastore> {

    override fun invoke(): Datastore =
        Datastore(
            environment = environment,
            dataStorePreferencesFactory = dataStorePreferencesFactory,
        )
}
