package com.multiplatform.td.core.navigation

abstract class FeatureRoute<T : Any> {
    abstract val route: T
    open val navOptions: FeatureNavOptions? = null
}
