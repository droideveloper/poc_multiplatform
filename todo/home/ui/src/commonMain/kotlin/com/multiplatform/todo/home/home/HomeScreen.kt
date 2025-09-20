package com.multiplatform.todo.home.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.multiiplatform.td.core.database.composable.LocalDatabaseComponent
import com.multiplatform.td.core.app.composable.AppContext
import com.multiplatform.td.core.app.composable.LocalAppComponent
import com.multiplatform.td.core.app.composable.LocalComponentStore
import com.multiplatform.td.core.app.composable.LocalNavController
import com.multiplatform.td.core.app.inject.store
import com.multiplatform.td.core.app.viewmodel.kotlinInjectViewModel
import com.multiplatform.todo.calendar.CalendarScreen
import com.multiplatform.todo.home.Route
import com.multiplatform.todo.home.TdBottomTabBar
import com.multiplatform.todo.home.TdFabButton
import com.multiplatform.todo.home.inject.HomeComponent
import com.multiplatform.todo.home.inject.createHomeComponent
import com.multiplatform.todo.home.main.MainScreen
import com.multiplatform.todo.settings.SettingsScreen
import com.multiplatform.todo.tasks.task.TasksScreen

@Composable
fun HomeScreen() {
    val component = rememberHomeComponent(
        navHostController = rememberNavController(),
    )
    AppContext(
        component = LocalAppComponent.current,
        navHostController = component.navHostController,
    ) {
        val viewModel = kotlinInjectViewModel(
            create = component.homeViewModelFactory,
        )
        HomeSuccessView(viewModel.state, viewModel::dispatch)
    }
}

@Composable
internal fun rememberHomeComponent(
    navHostController: NavHostController,
): HomeComponent {
    val databaseComponent = LocalDatabaseComponent.current

    val componentStore = LocalComponentStore.current

    return componentStore.store {
        createHomeComponent(
            databaseComponent = databaseComponent,
            navHostController = navHostController,
        )
    }
}

@Composable
private fun HomeSuccessView(
    state: HomeState,
    dispatch: (HomeEvent) -> Unit,
) {
    Scaffold(
        bottomBar = {
            TdBottomTabBar(
                selectedItem = state.selectedMenuItem,
                menuItems = state.menuItems,
                isSelected = state::isSelected,
                dispatch = dispatch,
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            TdFabButton(selectedItem = state.selectedMenuItem) {
                // TODO put add new action in here
            }
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = paddingValues.calculateStartPadding(LocalLayoutDirection.current),
                    end = paddingValues.calculateEndPadding(LocalLayoutDirection.current),
                ),
        ) {
            val navController = LocalNavController.current
            NavHost(navController = navController, startDestination = Route.Graph) {
                navigation<Route.Graph>(startDestination = Route.Main) {
                    composable<Route.Main> {
                        MainScreen(
                            onViewMoreClick = { dispatch(HomeEvent.OnMenuItemSelected(MenuItem.Task())) },
                            onViewCalendarClick = { dispatch(HomeEvent.OnMenuItemSelected(MenuItem.Calender())) },
                        )
                    }
                    composable<Route.Task> { TasksScreen() }
                    composable<Route.Calender> { CalendarScreen() }
                    composable<Route.Settings> { SettingsScreen() }
                }
            }
        }
    }
}
