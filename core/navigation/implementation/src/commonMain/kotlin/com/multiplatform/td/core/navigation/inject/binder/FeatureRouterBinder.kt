package com.multiplatform.td.core.navigation.inject.binder

import androidx.navigation.NavHostController
import com.multiplatform.td.core.injection.Binder
import com.multiplatform.td.core.navigation.FeatureRouter
import com.multiplatform.td.core.navigation.FeatureRouterImpl
import me.tatarka.inject.annotations.Inject

@Inject
class FeatureRouterBinder(
    private val navHostController: NavHostController,
) : Binder<FeatureRouter> {

    override fun invoke(): FeatureRouter =
        FeatureRouterImpl(navController = navHostController)
}
