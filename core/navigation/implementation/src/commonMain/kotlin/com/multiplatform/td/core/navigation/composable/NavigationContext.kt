package com.multiplatform.td.core.navigation.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import com.multiplatform.td.core.app.composable.LocalComponentStore
import com.multiplatform.td.core.app.composable.LocalNavController
import com.multiplatform.td.core.app.error.CompositionContextException
import com.multiplatform.td.core.app.inject.store
import com.multiplatform.td.core.navigation.inject.NavigationComponent
import com.multiplatform.td.core.navigation.inject.create

val LocalNavigationComponent: ProvidableCompositionLocal<NavigationComponent> = compositionLocalOf {
    throw CompositionContextException.NotFound(NavigationComponent::class)
}

@Composable
fun NavigationContext(
    navHostController: NavHostController = LocalNavController.current,
    content: @Composable () -> Unit,
) {
    val componentStore = LocalComponentStore.current

    val navigationComponent = componentStore.store {
        NavigationComponent.create(navHostController = navHostController)
    }

    CompositionLocalProvider(
        LocalNavigationComponent provides navigationComponent,
    ) {
        content()
    }
}
