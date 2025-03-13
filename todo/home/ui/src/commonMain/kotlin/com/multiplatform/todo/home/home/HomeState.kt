package com.multiplatform.todo.home.home

import com.multiplatform.todo.home.HomeRoute
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import tdmultiplatform.todo.home.ui.generated.resources.Res
import tdmultiplatform.todo.home.ui.generated.resources.ic_calendar
import tdmultiplatform.todo.home.ui.generated.resources.ic_home
import tdmultiplatform.todo.home.ui.generated.resources.ic_settings
import tdmultiplatform.todo.home.ui.generated.resources.ic_tasks

internal data class HomeState(
    val uiState: UiState = UiState.Loading,
    val selectedMenuItem: MenuItem = MenuItem.Home(),
    val menuItems: List<MenuItem> = DefaultMenuItems,
    // TODO Show bottom sheet dialog in here
) {
    fun isSelected(item: MenuItem): Boolean = item == selectedMenuItem
}

internal interface UiState {
    data object Loading : UiState
    data object Success : UiState

    sealed interface Failure : UiState {

        data class Text(val message: String) : Failure
        data class Res(val stringResource: StringResource) : Failure
    }
}

sealed class MenuItem(
    open val route: HomeRoute,
    open val iconRes: DrawableResource,
) {

    data class Home(
        override val route: HomeRoute = HomeRoute.Main,
        override val iconRes: DrawableResource = Res.drawable.ic_home,
    ) : MenuItem(route, iconRes)

    data class Task(
        override val route: HomeRoute = HomeRoute.Task,
        override val iconRes: DrawableResource = Res.drawable.ic_tasks,
    ) : MenuItem(route, iconRes)

    data class Calender(
        override val route: HomeRoute = HomeRoute.Calendar,
        override val iconRes: DrawableResource = Res.drawable.ic_calendar,
    ) : MenuItem(route, iconRes)

    data class Settings(
        override val route: HomeRoute = HomeRoute.Settings,
        override val iconRes: DrawableResource = Res.drawable.ic_settings,
    ) : MenuItem(route, iconRes)
}

private val DefaultMenuItems = listOf(
    MenuItem.Home(),
    MenuItem.Task(),
    MenuItem.Calender(),
    MenuItem.Settings(),
)

