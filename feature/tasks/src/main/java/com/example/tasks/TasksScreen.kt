package com.example.tasks

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.design_system.component.EmptyTask
import com.example.design_system.component.Loading
import com.example.design_system.component.TaskCard
import com.example.design_system.theme.TodoTheme
import com.example.model.Task
import com.example.tasks.model.TasksUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun TasksRoute(
    viewModel: TasksViewModel = hiltViewModel(),
    title: String,
    popBackStack: () -> Unit,
    navigateEditTask: (Long) -> Unit,
    onShowErrorSnackbar: (throwable: Throwable?) -> Unit,
    onShowMessageSnackBar: (message: String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(title) {
        viewModel.fetchTasks(title)
    }

    LaunchedEffect(true) {
        viewModel.errorFlow.collectLatest { throwable ->
            onShowErrorSnackbar(throwable)
        }
    }

    TasksContent(
        uiState = uiState,
        title = title,
        popBackStack = popBackStack,
        navigateEditTask = navigateEditTask,
        onTaskToggleCompletion = { taskId, isCompleted ->
            viewModel.toggleTaskCompletion(
                taskId = taskId,
                isCompleted = isCompleted
            )
        },
        onTaskDelete = viewModel::deleteTask,
        onShowMessageSnackBar = onShowMessageSnackBar,
    )
}

@Composable
private fun TasksContent(
    uiState: TasksUiState,
    title: String,
    popBackStack: () -> Unit,
    navigateEditTask: (Long) -> Unit,
    onTaskToggleCompletion: (Long, Boolean) -> Unit,
    onTaskDelete: (Long) -> Unit,
    onShowMessageSnackBar: (message: String) -> Unit,
) {
    when (uiState) {
        is TasksUiState.Loading -> {
            Loading()
        }

        is TasksUiState.Success -> {
            TasksScreen(
                title = title,
                tasks = uiState.tasks,
                popBackStack = popBackStack,
                navigateEditTask = navigateEditTask,
                onTaskToggleCompletion = onTaskToggleCompletion,
                onTaskDelete = onTaskDelete,
                onShowMessageSnackBar = onShowMessageSnackBar
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TasksScreen(
    title: String,
    tasks: ImmutableList<Task>,
    popBackStack: () -> Unit,
    navigateEditTask: (Long) -> Unit,
    onTaskToggleCompletion: (Long, Boolean) -> Unit,
    onTaskDelete: (Long) -> Unit,
    onShowMessageSnackBar: (message: String) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                title = {
                    Text(
                        text = title,
                        style = TodoTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = popBackStack) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = ImageVector.vectorResource(R.drawable.svg_arrow_left),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
            )
        }
    ) { paddingValues ->
        if (tasks.isEmpty()) {
            EmptyTask(
                modifier = Modifier.fillMaxSize()
                    .padding(paddingValues)
                    .background(MaterialTheme.colorScheme.surface)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
                    .padding(paddingValues)
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 10.dp,
                    )
            ) {
                itemsIndexed(
                    items = tasks,
                    key = { _, task ->
                        task.id
                    }
                ) { _, task ->
                    Box(
                        modifier = Modifier.animateItem(tween(500))
                    ) {
                        TaskCard(
                            task = task,
                            isAvailableSwipe = false,
                            onTaskEdit = { taskId -> navigateEditTask(taskId) },
                            onTaskToggleCompletion = onTaskToggleCompletion,
                            onTaskDelete = { taskId -> onTaskDelete(taskId) },
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}