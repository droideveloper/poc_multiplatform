package com.multiplatform.todo.onboarding.inject

import androidx.compose.runtime.Composable
import com.multiplatform.td.core.app.composable.LocalComponentStore
import com.multiplatform.td.core.app.inject.store
import com.multiplatform.td.core.datastore.composable.LocalDataSoreComponent
import com.multiplatform.td.core.navigation.composable.LocalNavigationComponent

@Composable
internal fun rememberOnboardingComponent(): OnboardingComponent {
    val dataStoreComponent = LocalDataSoreComponent.current
    val navigationComponent = LocalNavigationComponent.current

    val componentStore = LocalComponentStore.current

    return componentStore.store {
        createOnboardingComponent(
            dataStoreComponent = dataStoreComponent,
            navigationComponent = navigationComponent,
        )
    }
}
