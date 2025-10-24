package com.multiplatform.todo.home.main

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.multiplatform.td.core.app.composable.LocalNavController
import com.multiplatform.td.core.app.viewmodel.kotlinInjectViewModel
import com.multiplatform.td.core.navigation.composable.LocalNavigationComponent
import com.multiplatform.td.core.navigation.composable.NavigationContext
import com.multiplatform.td.core.ui.KoverIgnore
import com.multiplatform.td.core.ui.TdTheme
import com.multiplatform.td.core.ui.button.TdTextLinkButton
import com.multiplatform.td.core.ui.extensions.ignoreHorizontalPadding
import com.multiplatform.td.core.ui.navbar.NavBarDefaults
import com.multiplatform.todo.core.ui.CalendarTimeLazyLayout
import com.multiplatform.todo.core.ui.TdNavBar
import com.multiplatform.todo.core.ui.rememberLocalTimeItems
import com.multiplatform.todo.home.TodayTask
import com.multiplatform.todo.home.home.rememberHomeComponent
import com.multiplatform.todo.home.home.selectSecondaryTitle
import com.multiplatform.todo.tasks.Category
import com.multiplatform.todo.tasks.CategoryColor
import com.multiplatform.todo.tasks.Task
import com.multiplatform.todo.tasks.TaskStatus
import com.multiplatform.todo.tasks.TaskSubTasks
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import tdmultiplatform.todo.home.ui.generated.resources.Res
import tdmultiplatform.todo.home.ui.generated.resources.ic_filter
import tdmultiplatform.todo.home.ui.generated.resources.main_tasks_for_today_title
import tdmultiplatform.todo.home.ui.generated.resources.main_title
import tdmultiplatform.todo.home.ui.generated.resources.main_today_schedule_title
import tdmultiplatform.todo.home.ui.generated.resources.main_view_calendar_title
import tdmultiplatform.todo.home.ui.generated.resources.main_view_more_title
import kotlin.time.Duration.Companion.minutes

@KoverIgnore
@Composable
internal fun MainScreen(
    onViewMoreClick: () -> Unit,
    onViewCalendarClick: () -> Unit,
) {
    val featureRouter = LocalNavigationComponent.current.featureRouter
    NavigationContext {
        val component = rememberHomeComponent(
            navHostController = LocalNavController.current,
        )
        val viewModel = kotlinInjectViewModel(
            create = component.mainViewModelFactory,
            param = featureRouter,
        )
        MainUi(viewModel.state, viewModel::dispatch, onViewMoreClick, onViewCalendarClick)
    }
}

@KoverIgnore
@Composable
private fun MainUi(
    state: MainState,
    dispatch: (MainEvent) -> Unit,
    onViewMoreClick: () -> Unit,
    onViewCalendarClick: () -> Unit,
) {
    Crossfade(
        targetState = state.uiState,
    ) { uiState ->
        when (uiState) {
            UiState.Loading -> Unit // TODO implement this
            is UiState.Failure -> Unit // TODO implement this
            UiState.Success -> MainSuccessView(state, dispatch, onViewMoreClick, onViewCalendarClick)
        }
    }
}

@Composable
internal fun MainSuccessView(
    state: MainState,
    dispatch: (MainEvent) -> Unit,
    onViewMoreClick: () -> Unit,
    onViewCalendarClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            TdNavBar(
                title = stringResource(Res.string.main_title),
                secondaryTitle = selectSecondaryTitle(),
                action = NavBarDefaults.Action.Icon(
                    icon = rememberVectorPainter(vectorResource(Res.drawable.ic_filter)),
                    onClick = { dispatch(MainEvent.OnFilterClicked) },
                    tint = TdTheme.colors.greys.primary,
                ),
            )
        },
    ) { paddingValues ->
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(state = scrollState)
                .background(color = TdTheme.colors.whites.primary),
        ) {
            Spacer(modifier = Modifier.height(TdTheme.dimens.standard36))
            TodayScheduleSection(state.todayTasks, onViewMoreClick, dispatch)
            Spacer(modifier = Modifier.height(TdTheme.dimens.standard24))
            TodayTasksSection(state.todayScheduleTasks, dispatch, onViewCalendarClick)
        }
    }
}

@Composable
private fun TodayScheduleSection(
    todayTasks: List<TaskSubTasks>,
    onViewMoreClick: () -> Unit,
    dispatch: (MainEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = TdTheme.dimens.standard16),
    ) {
        TodaySectionHeader(
            title = stringResource(Res.string.main_tasks_for_today_title),
            secondaryTitle = stringResource(Res.string.main_view_more_title),
            onClick = onViewMoreClick,
        )
        Spacer(modifier = Modifier.height(TdTheme.dimens.standard16))
        LazyRow(
            modifier = Modifier
                .ignoreHorizontalPadding(TdTheme.dimens.standard16)
                .sizeIn(maxHeight = 256.dp)
                .fillMaxWidth(),
            state = rememberLazyListState(),
        ) {
            items(
                items = todayTasks,
                key = { it.task.id },
            ) { task ->
                TodayTask(
                    progress = {
                        when (val progress = task.selectProgress()) {
                            Float.NaN -> 0F
                            else -> progress
                        }
                    },
                    task = task.task,
                    onCategoryClick = { dispatch(MainEvent.OnCategoryClicked(it)) },
                    onTaskClick = { dispatch(MainEvent.OnTaskClicked(it)) },
                )
            }
        }
    }
}

@Composable
private fun TodaySectionHeader(
    title: String,
    secondaryTitle: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = title,
            style = TextStyle.Default.copy(
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold,
                color = TdTheme.colors.blacks.primary,
            ),
        )
        TdTextLinkButton(
            text = secondaryTitle,
            textStyle = TextStyle.Default.copy(
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
            ),
            onClick = onClick,
            textColor = TdTheme.colors.deepOranges.primary,
            pressedTextColor = TdTheme.colors.deepOranges.primary,
        )
    }
}

@Composable
private fun TodayTasksSection(
    todayScheduleTask: List<Task>,
    dispatch: (MainEvent) -> Unit,
    onViewCalendarClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = TdTheme.dimens.standard16),
    ) {
        TodaySectionHeader(
            title = stringResource(Res.string.main_today_schedule_title),
            secondaryTitle = stringResource(Res.string.main_view_calendar_title),
            onClick = onViewCalendarClick,
        )
        Spacer(modifier = Modifier.height(TdTheme.dimens.standard16))
        val task = todayScheduleTask.firstOrNull() ?: return
        val localTimes = rememberLocalTimeItems(task.dueDateTime.time)
        CalendarTimeLazyLayout(
            modifier = Modifier
                .height(TdTheme.dimens.standard96 * localTimes.size)
                .fillMaxWidth(),
            localTimes = localTimes,
            time = task.dueDateTime.time,
            itemModifier = Modifier.height(TdTheme.dimens.standard96),
            taskMap = todayScheduleTask.selectTaskComposable(
                onTaskClick = { dispatch(MainEvent.OnTaskClicked(it)) },
                onCategoryClick = { dispatch(MainEvent.OnCategoryClicked(it)) },
            ),
        )
    }
}

internal val date = LocalDate(2025, 10, 21)
internal val time = LocalTime(10, 0, 0)

internal val category = Category(
    id = 0,
    name = "Health",
    description = "Health and related stuff on wel-being",
    color = CategoryColor.getOrThrow(-6440513913749504),
    iconRes = null,
)

internal val task = Task(
    id = 0,
    category = category,
    title = "Doctor Appointment",
    description = "Actually it is dental appointment",
    dueDateTime = LocalDateTime(date, time),
    duration = 45.minutes,
    status = TaskStatus.OverDue,
)

private val tasks = listOf(
    task.copy(
        id = 0,
    ),
    task.copy(
        id = 1,
        dueDateTime = LocalDateTime(date, LocalTime(12, 0, 0)),
    ),
    task.copy(
        id = 1,
        dueDateTime = LocalDateTime(date, LocalTime(14, 0, 0)),
    ),
)

internal fun Task.asSubTask(): TaskSubTasks = TaskSubTasks(task = this, tasks = emptyList())

@Preview(
    showBackground = true,
)
@Composable
private fun TodayScheduleSectionPreview() {
    TdTheme {
        TodayScheduleSection(
            todayTasks = tasks.map { it.asSubTask() },
            dispatch = { },
            onViewMoreClick = { },
        )
    }
}

@Preview(
    showBackground = true,
)
@Composable
private fun TodayTasksSectionPreview() {
    TdTheme {
        TodayTasksSection(
            todayScheduleTask = tasks,
            dispatch = { },
            onViewCalendarClick = { },
        )
    }
}

@Preview(
    showBackground = true,
)
@Composable
private fun MainSuccessViewPreview() {
    TdTheme {
        MainSuccessView(
            state = MainState(
                uiState = UiState.Success,
                todayTasks = tasks.map { it.asSubTask() },
                todayScheduleTasks = tasks,
                date = date,
                time = LocalTime(8, 0, 0),
            ),
            dispatch = { },
            onViewMoreClick = { },
            onViewCalendarClick = { },
        )
    }
}
