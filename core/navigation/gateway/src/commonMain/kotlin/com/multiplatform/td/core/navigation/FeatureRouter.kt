package com.multiplatform.td.core.navigation

interface FeatureRouter {

    fun <T : Any> navigate(route: FeatureRoute<T>)
    fun <T : Any> navigate(route: FeatureRoute<T>, options: FeatureNavOptions)
    fun <T : Any> restart(route: FeatureRoute<T>)
    fun back()
}
