package com.multiplatform.td.core.app.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import com.multiplatform.td.core.app.AppComponent
import com.multiplatform.td.core.app.error.CompositionContextException
import com.multiplatform.td.core.app.inject.ComponentStore
import com.multiplatform.td.core.app.inject.ComponentStoreImpl

val LocalAppComponent: ProvidableCompositionLocal<AppComponent> = compositionLocalOf {
    throw CompositionContextException.NotFound(AppComponent::class)
}

val LocalNavController: ProvidableCompositionLocal<NavHostController> = compositionLocalOf {
    throw CompositionContextException.NotFound(NavHostController::class)
}

val LocalComponentStore: ProvidableCompositionLocal<ComponentStore> = compositionLocalOf {
    throw CompositionContextException.NotFound(ComponentStore::class)
}

@Composable
fun AppContext(
    component: AppComponent,
    navHostController: NavHostController,
    content: @Composable () -> Unit,
) {
    val componentStore: ComponentStore = remember { ComponentStoreImpl() }
    CompositionLocalProvider(
        LocalAppComponent provides component,
        LocalNavController provides navHostController,
        LocalComponentStore provides componentStore,
    ) {
        content()
    }
}
