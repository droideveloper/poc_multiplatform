package com.multiplatform.td.core.navigation.inject

import androidx.navigation.NavHostController
import com.multiplatform.td.core.navigation.FeatureRouter
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@Component
abstract class NavigationComponent(
    @get:Provides val navHostController: NavHostController,
) : NavigationModule {
    companion object;

    abstract val featureRouter: FeatureRouter
}