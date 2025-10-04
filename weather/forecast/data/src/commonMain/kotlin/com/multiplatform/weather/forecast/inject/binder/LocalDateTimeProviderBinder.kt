package com.multiplatform.weather.forecast.inject.binder

import com.multiplatform.td.core.environment.Environment
import com.multiplatform.td.core.injection.Binder
import com.multiplatform.weather.forecast.LocalDateTimeProvider
import com.multiplatform.weather.forecast.LocalDateTimeProviderImpl
import me.tatarka.inject.annotations.Inject

@Inject
class LocalDateTimeProviderBinder(
    private val environment: Environment,
) : Binder<LocalDateTimeProvider> {

    override fun invoke(): LocalDateTimeProvider =
        LocalDateTimeProviderImpl(
            environment = environment,
        )
}
