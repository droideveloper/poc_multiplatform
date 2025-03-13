package com.multiplatform.weather.onboarding.inject

import com.multiplatform.td.core.datastore.inject.DataStoreComponent
import com.multiplatform.td.core.injection.scopes.FeatureScope
import com.multiplatform.td.core.navigation.inject.NavigationComponent
import com.multiplatform.weather.onboarding.GeneratedViewModelModule
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.KmpComponentCreate

@FeatureScope
@Component
abstract class OnboardingComponent(
    @Component val dataStoreComponent: DataStoreComponent,
    @Component val navigationComponent: NavigationComponent,
) : GeneratedViewModelModule(), OnboardingDataModule {
    companion object;
}

@KmpComponentCreate
expect fun createOnboardingComponent(
    dataStoreComponent: DataStoreComponent,
    navigationComponent: NavigationComponent,
): OnboardingComponent
