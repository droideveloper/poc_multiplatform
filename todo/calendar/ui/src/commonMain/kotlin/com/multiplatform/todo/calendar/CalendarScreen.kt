package com.multiplatform.todo.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import com.multiiplatform.td.core.database.composable.LocalDatabaseComponent
import com.multiplatform.td.core.app.composable.LocalComponentStore
import com.multiplatform.td.core.app.inject.store
import com.multiplatform.td.core.app.viewmodel.kotlinInjectViewModel
import com.multiplatform.td.core.navigation.composable.LocalNavigationComponent
import com.multiplatform.td.core.ui.TdTheme
import com.multiplatform.td.core.ui.navbar.NavBarDefaults
import com.multiplatform.todo.calendar.inject.CalendarComponent
import com.multiplatform.todo.calendar.inject.createCalendarComponent
import com.multiplatform.todo.core.ui.CalendarTimeLazyLayout
import com.multiplatform.todo.core.ui.TdNavBar
import com.multiplatform.todo.core.ui.rememberLocalTimeItems
import com.multiplatform.todo.tasks.Task
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import tdmultiplatform.todo.calendar.ui.generated.resources.Res
import tdmultiplatform.todo.calendar.ui.generated.resources.calendar_title
import tdmultiplatform.todo.calendar.ui.generated.resources.ic_filter

@Composable
fun CalendarScreen() {
    val component = rememberCalendarComponent()
    val viewModel = kotlinInjectViewModel(
        create = component.calendarViewModelFactory,
    )
    CalendarUi(viewModel.state, viewModel::dispatch)
}

@Composable
private fun rememberCalendarComponent(): CalendarComponent {
    val navigationComponent = LocalNavigationComponent.current
    val databaseComponent = LocalDatabaseComponent.current

    val componentStore = LocalComponentStore.current

    return componentStore.store {
        createCalendarComponent(
            databaseComponent = databaseComponent,
            navigationComponent = navigationComponent,
        )
    }
}

@Composable
private fun CalendarUi(
    state: CalendarState,
    dispatch: (CalendarEvent) -> Unit,
) {
    when (val uiState = state.uiState) {
        UiState.Loading -> Unit // TODO replace it later
        is UiState.Failure -> Unit // TODO replace it later
        UiState.Success -> CalendarSuccessView(state, dispatch)
    }
}

@Composable
private fun CalendarSuccessView(
    state: CalendarState,
    dispatch: (CalendarEvent) -> Unit,
) {
    Scaffold(
        topBar = {
            TdNavBar(
                title = stringResource(Res.string.calendar_title),
                secondaryTitle = selectSecondaryTitle(),
                action = NavBarDefaults.Action.Icon(
                    icon = rememberVectorPainter(vectorResource(Res.drawable.ic_filter)),
                    onClick = { dispatch(CalendarEvent.OnFilterClicked) },
                    tint = TdTheme.colors.greys.primary,
                )
            )
        }
    ) { paddingValues ->
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(state = scrollState)
                .background(color = TdTheme.colors.whites.primary)
        ) {
            Spacer(modifier = Modifier.height(TdTheme.dimens.standard36))
            CalendarWeekHeader(state.week, dispatch)
            Spacer(modifier = Modifier.height(TdTheme.dimens.standard8))
            CalendarTodayTasks(state.tasks, dispatch)
        }
    }
}

@Composable
private fun CalendarWeekHeader(
    week: Week,
    dispatch: (CalendarEvent) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(state = rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(
            space = TdTheme.dimens.standard8,
        ),
    ) {
        week.days.forEach { day ->
            CalendarDay(
                modifier = Modifier.width(TdTheme.dimens.standard64),
                day = day,
                isSelected = day == week.today,
                onClick = { dispatch(CalendarEvent.OnDateClicked(day)) },
            )
        }
    }
}

@Composable
private fun CalendarTodayTasks(
    tasks: List<Task>,
    dispatch: (CalendarEvent) -> Unit,
) {
    val task = tasks.firstOrNull() ?: return
    val localTimes = rememberLocalTimeItems(task.dueDateTime.time)
    CalendarTimeLazyLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(TdTheme.dimens.standard16)
            .height(TdTheme.dimens.standard96 * localTimes.size),
        localTimes = localTimes,
        time = task.dueDateTime.time,
        itemModifier = Modifier.height(TdTheme.dimens.standard96),
        taskMap = tasks.selectTaskComposable(
            onTaskClick = { dispatch(CalendarEvent.OnTaskClicked(it)) },
        ),
    )
}
