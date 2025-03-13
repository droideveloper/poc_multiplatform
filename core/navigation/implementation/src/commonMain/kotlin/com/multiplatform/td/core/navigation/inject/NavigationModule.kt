package com.multiplatform.td.core.navigation.inject

import com.multiplatform.td.core.navigation.FeatureRouter
import com.multiplatform.td.core.navigation.inject.binder.FeatureRouterBinder
import me.tatarka.inject.annotations.Provides

interface NavigationModule {

    @Provides
    fun bindFeatureRouter(binder: FeatureRouterBinder): FeatureRouter = binder()
}
