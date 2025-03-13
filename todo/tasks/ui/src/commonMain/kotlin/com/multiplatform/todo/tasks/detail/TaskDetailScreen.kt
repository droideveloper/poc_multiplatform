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
import com.multiplatform.td.core.ui.TdTheme
import com.multiplatform.td.core.ui.effects.OnScreenStart
import com.multiplatform.td.core.ui.navbar.NavBarDefaults
import com.multiplatform.todo.core.ui.TdNavBar
import com.multiplatform.todo.tasks.Category
import com.multiplatform.todo.tasks.Task
import com.multiplatform.todo.tasks.TaskStatus
import com.multiplatform.todo.tasks.selectContainerColor
import com.multiplatform.todo.tasks.selectLocalDate
import com.multiplatform.todo.tasks.task.rememberTaskComponent
import kotlinx.datetime.LocalDateTime

@Composable
fun TaskDetailScreen(taskId: Long) {
    val component = rememberTaskComponent()
    val viewModel = kotlinInjectViewModel(
        create = component.taskDetailViewModelFactory,
        param = taskId,
    )
    TaskDetailUi(viewModel.state, viewModel::dispatch)
}

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
private fun TaskDetailSuccessView(
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
        }
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
            TaskStatusDetails(task.dueDateTime, task.status)
        }
    }
}

@Composable
private fun TaskDetailsHeader(
    category: Category,
    title: String,
    description: String,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = category.name,
            style = TextStyle.Default.copy(
                color = selectContainerColor(category.color),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
            )
        )
        Spacer(modifier = Modifier.height(TdTheme.dimens.standard8))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = title,
            style = TextStyle.Default.copy(
                color = TdTheme.colors.blacks.secondary,
                fontSize = 25.sp,
                fontWeight = FontWeight.Medium,
            )
        )
        Spacer(modifier = Modifier.height(TdTheme.dimens.standard8))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = description,
            style = TextStyle.Default.copy(
                color = TdTheme.colors.blacks.light,
                fontSize = 16.sp,
                fontWeight = FontWeight.Light,
            )
        )
    }
}

@Composable
private fun TaskStatusDetails(
    date: LocalDateTime,
    status: TaskStatus,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = status.name.lowercase(),
            style = TextStyle.Default.copy(
                color = TdTheme.colors.blacks.light,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
            )
        )
        Spacer(modifier = Modifier.height(TdTheme.dimens.standard8))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = selectLocalDate(date.date),
            style = TextStyle.Default.copy(
                color = TdTheme.colors.blacks.light,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
            )
        )
    }
}
