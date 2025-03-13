package com.multiplatform.todo.home.home

internal sealed interface HomeEvent {

    data object OnScreenViewed : HomeEvent
    data object OnNewClicked : HomeEvent
    data class OnMenuItemSelected(
        val item: MenuItem
    ) : HomeEvent
}