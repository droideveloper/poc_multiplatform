package com.multiplatform.todo.home.home

import com.multiplatform.td.core.injection.binding.ContributesViewModel
import com.multiplatform.td.core.injection.scopes.FeatureScope
import com.multiplatform.td.core.mvi.MviViewModel
import com.multiplatform.td.core.navigation.FeatureNavOptions
import com.multiplatform.td.core.navigation.FeatureRouter
import com.multiplatform.todo.home.Route

@ContributesViewModel(scope = FeatureScope::class)
internal class HomeViewModel(
    private val featureRouter: FeatureRouter
) : MviViewModel<HomeEvent, HomeState>(
    initialState = HomeState()
) {

    init {
        on<HomeEvent.OnScreenViewed> {
            // Todo log or analytics
        }
        onClick<HomeEvent.OnNewClicked> {
            // TODO implement this on depending on current navigation
        }
        onClick<HomeEvent.OnMenuItemSelected> {
            state = state.copy(selectedMenuItem = it.item)
            featureRouter.navigate(
                route = it.item.route,
                // options will wipe backstack when we move next tab,
                // which is fine for us since we are using
                // local data anyway
                options = FeatureNavOptions.Builder().apply {
                    inclusive(true)
                    popUpTo(Route.Graph)
                }
                    .build()
            )
        }
    }
}