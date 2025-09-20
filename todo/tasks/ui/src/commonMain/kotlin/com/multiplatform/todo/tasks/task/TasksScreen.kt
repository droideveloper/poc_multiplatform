package com.multiplatform.todo.tasks.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import com.multiplatform.todo.core.ui.TdNavBar
import com.multiplatform.todo.tasks.TaskItemView
import com.multiplatform.todo.tasks.inject.TasksComponent
import com.multiplatform.todo.tasks.inject.createTasksComponent
import com.multiplatform.todo.tasks.selectSecondaryTitle
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import tdmultiplatform.todo.tasks.ui.generated.resources.Res
import tdmultiplatform.todo.tasks.ui.generated.resources.ic_filter
import tdmultiplatform.todo.tasks.ui.generated.resources.tasks_title

@Composable
fun TasksScreen() {
    val component = rememberTaskComponent()
    val viewModel = kotlinInjectViewModel(
        create = component.tasksViewModelFactory,
    )
    TasksUi(viewModel.state, viewModel::dispatch)
}

@Composable
internal fun rememberTaskComponent(): TasksComponent {
    val databaseComponent = LocalDatabaseComponent.current
    val navigationComponent = LocalNavigationComponent.current

    val componentStore = LocalComponentStore.current

    return componentStore.store {
        createTasksComponent(
            databaseComponent = databaseComponent,
            navigationComponent = navigationComponent,
        )
    }
}

@Composable
private fun TasksUi(
    state: TasksState,
    dispatch: (TasksEvent) -> Unit,
) {
    when (val uiState = state.uiState) {
        UiState.Loading -> Unit // TODO replace it later
        is UiState.Failure -> Unit // TODO replace it later
        UiState.Success -> TasksSuccessView(state, dispatch)
    }
}

@Composable
private fun TasksSuccessView(
    state: TasksState,
    dispatch: (TasksEvent) -> Unit,
) {
    Scaffold(
        topBar = {
            TdNavBar(
                title = stringResource(Res.string.tasks_title),
                secondaryTitle = selectSecondaryTitle(),
                action = NavBarDefaults.Action.Icon(
                    icon = rememberVectorPainter(vectorResource(Res.drawable.ic_filter)),
                    onClick = { dispatch(TasksEvent.OnFilterClicked) },
                    tint = TdTheme.colors.greys.primary,
                ),
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = TdTheme.dimens.standard16)
                .background(color = TdTheme.colors.whites.primary),
        ) {
            val scrollState = rememberLazyListState()
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                state = scrollState,
                verticalArrangement = Arrangement.spacedBy(
                    space = TdTheme.dimens.standard8,
                ),
            ) {
                item {
                    Spacer(modifier = Modifier.height(TdTheme.dimens.standard36))
                }
                items(state.tasks) { task ->
                    TaskItemView(
                        task = task,
                        date = task.dueDateTime.date,
                        onTaskClicked = { dispatch(TasksEvent.OnTaskClicked(it)) },
                    )
                }
            }
        }
    }
}
