package com.multiplatform.td.core.network.inject.binder

import com.multiplatform.td.core.injection.Binder
import com.multiplatform.td.core.network.KtorfitBuilderFactory
import com.multiplatform.td.core.network.Url
import de.jensklingenberg.ktorfit.Ktorfit
import me.tatarka.inject.annotations.Inject

@Inject
class KtorfitBinder(
    private val ktorfitBuilderFactory: KtorfitBuilderFactory,
    private val url: Url
) : Binder<Ktorfit> {

    override fun invoke(): Ktorfit =
        ktorfitBuilderFactory()
            .baseUrl(url.value)
            .build()
}