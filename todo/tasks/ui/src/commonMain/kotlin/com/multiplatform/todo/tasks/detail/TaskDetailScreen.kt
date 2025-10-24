package com.multiplatform.todo.tasks.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.multiplatform.td.core.app.viewmodel.kotlinInjectViewModel
import com.multiplatform.td.core.ui.KoverIgnore
import com.multiplatform.td.core.ui.TdTheme
import com.multiplatform.td.core.ui.effects.OnScreenStart
import com.multiplatform.td.core.ui.navbar.NavBarDefaults
import com.multiplatform.todo.core.ui.TdNavBar
import com.multiplatform.todo.tasks.Category
import com.multiplatform.todo.tasks.CategoryColor
import com.multiplatform.todo.tasks.Task
import com.multiplatform.todo.tasks.TaskStatus
import com.multiplatform.todo.tasks.selectContainerColor
import com.multiplatform.todo.tasks.selectLocalDate
import com.multiplatform.todo.tasks.task.rememberTaskComponent
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider
import kotlin.time.Duration.Companion.minutes

@KoverIgnore
@Composable
fun TaskDetailScreen(taskId: Long) {
    val component = rememberTaskComponent()
    val viewModel = kotlinInjectViewModel(
        create = component.taskDetailViewModelFactory,
        param = taskId,
    )
    TaskDetailUi(viewModel.state, viewModel::dispatch)
}

@KoverIgnore
@Composable
private fun TaskDetailUi(
    state: TaskDetailState,
    dispatch: (TaskDetailEvent) -> Unit,
) {
    when (val uiState = state.uiState) {
        UiState.Loading -> Unit
        is UiState.Failure -> Unit
        is UiState.Success -> TaskDetailSuccessView(uiState.task, dispatch)
    }
    OnScreenStart { dispatch(TaskDetailEvent.OnScreenViewed) }
}

@Composable
internal fun TaskDetailSuccessView(
    task: Task,
    dispatch: (TaskDetailEvent) -> Unit,
) {
    Scaffold(
        topBar = {
            TdNavBar(
                title = task.title,
                secondaryTitle = task.description,
                navAction = NavBarDefaults.ArrowBackAction {
                    dispatch(TaskDetailEvent.OnBackClicked)
                },
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = TdTheme.dimens.standard16)
                .background(color = TdTheme.colors.whites.primary),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TaskDetailsHeader(task.category, task.title, task.description)
            Spacer(modifier = Modifier.height(TdTheme.dimens.standard16))
            TaskStatusDetails(task.dueDateTime.date, task.dueDateTime.time, task.status)
        }
    }
}

@Composable
internal fun TaskDetailsHeader(
    category: Category,
    title: String,
    description: String,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = category.name,
            style = TextStyle.Default.copy(
                color = selectContainerColor(category.color),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
            ),
        )
        Spacer(modifier = Modifier.height(TdTheme.dimens.standard8))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = title,
            style = TextStyle.Default.copy(
                color = TdTheme.colors.blacks.secondary,
                fontSize = 25.sp,
                fontWeight = FontWeight.Medium,
            ),
        )
        Spacer(modifier = Modifier.height(TdTheme.dimens.standard8))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = description,
            style = TextStyle.Default.copy(
                color = TdTheme.colors.blacks.light,
                fontSize = 16.sp,
                fontWeight = FontWeight.Light,
            ),
        )
    }
}

@Composable
internal fun TaskStatusDetails(
    date: LocalDate,
    time: LocalTime,
    status: TaskStatus,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = status.name.lowercase(),
            style = TextStyle.Default.copy(
                color = TdTheme.colors.blacks.light,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
            ),
        )
        Spacer(modifier = Modifier.height(TdTheme.dimens.standard8))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = selectLocalDate(date),
            style = TextStyle.Default.copy(
                color = TdTheme.colors.blacks.light,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
            ),
        )
    }
}

private class TaskStatusParameterProvider : PreviewParameterProvider<TaskStatus> {
    override val values: Sequence<TaskStatus> = sequenceOf(
        TaskStatus.Progress,
        TaskStatus.Done,
        TaskStatus.OverDue,
        TaskStatus.Open,
    )
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

@Preview(
    showBackground = true,
)
@Composable
private fun TaskStatusDetailsPreview(
    @PreviewParameter(TaskStatusParameterProvider::class) status: TaskStatus,
) {
    TdTheme {
        TaskStatusDetails(date, time, status)
    }
}

@Preview(
    showBackground = true,
)
@Composable
private fun TaskDetailsHeaderPreview() {
    TdTheme {
        TaskDetailsHeader(
            category = category,
            title = "Doctor Appointment",
            description = "Actually it is dental appointment",
        )
    }
}

@Preview(
    showBackground = true,
)
@Composable
private fun TaskDetailSuccessViewPreview() {
    TdTheme {
        TaskDetailSuccessView(
            task = task,
            dispatch = { },
        )
    }
}
