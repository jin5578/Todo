package com.example.home

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.design_system.component.EmptyContent
import com.example.design_system.component.Loading
import com.example.design_system.component.SortTaskDialog
import com.example.design_system.component.TaskCard
import com.example.design_system.theme.TodoTheme
import com.example.home.component.SwipeActionBox
import com.example.home.component.TaskInfoCard
import com.example.home.model.HomeUiState
import com.example.model.Category
import com.example.model.SortTask
import com.example.model.Task
import com.example.model.Theme
import com.example.model.ThemeType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import com.example.design_system.R as DesignSystemR

@Composable
internal fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateCalendar: () -> Unit,
    navigateSetting: () -> Unit,
    navigateAddTask: () -> Unit,
    navigateCompletedTask: (String) -> Unit,
    navigateIncompleteTask: (String) -> Unit,
    navigateThisWeekTask: (String) -> Unit,
    navigateAllTask: (String) -> Unit,
    navigateEditTask: (Long) -> Unit,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
    onShowMessageSnackBar: (message: String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.errorFlow.collectLatest { throwable ->
            onShowErrorSnackBar(throwable)
        }
    }

    HomeContent(
        uiState = uiState,
        navigateCalendar = navigateCalendar,
        navigateSetting = navigateSetting,
        navigateAddTask = navigateAddTask,
        navigateCompletedTask = navigateCompletedTask,
        navigateIncompleteTask = navigateIncompleteTask,
        navigateThisWeekTask = navigateThisWeekTask,
        navigateAllTask = navigateAllTask,
        navigateEditTask = navigateEditTask,
        onSortTaskChanged = viewModel::updateSortTask,
        onTaskDelete = viewModel::deleteTask,
        onTaskToggleCompletion = { taskId, isChecked ->
            viewModel.toggleTaskCompletion(
                taskId = taskId,
                isCompleted = isChecked
            )
        },
        onShowMessageSnackBar = onShowMessageSnackBar,
    )
}

@Composable
private fun HomeContent(
    uiState: HomeUiState,
    navigateCalendar: () -> Unit,
    navigateSetting: () -> Unit,
    navigateAddTask: () -> Unit,
    navigateCompletedTask: (String) -> Unit,
    navigateIncompleteTask: (String) -> Unit,
    navigateThisWeekTask: (String) -> Unit,
    navigateAllTask: (String) -> Unit,
    navigateEditTask: (Long) -> Unit,
    onSortTaskChanged: (SortTask) -> Unit,
    onTaskDelete: (id: Long, uuid: String) -> Unit,
    onTaskToggleCompletion: (Long, Boolean) -> Unit,
    onShowMessageSnackBar: (message: String) -> Unit,
) {
    when (uiState) {
        is HomeUiState.Loading -> {
            Loading()
        }

        is HomeUiState.Success -> {
            HomeScreen(
                completedTasks = uiState.completedTasks,
                incompleteTasks = uiState.incompleteTasks,
                categories = uiState.categories,
                sleepTime = uiState.sleepTime,
                sortTask = uiState.sortTask,
                theme = uiState.theme,
                buildVersion = uiState.buildVersion,
                navigateCalendar = navigateCalendar,
                navigateSetting = navigateSetting,
                navigateAddTask = navigateAddTask,
                navigateCompletedTask = navigateCompletedTask,
                navigateIncompleteTask = navigateIncompleteTask,
                navigateThisWeekTask = navigateThisWeekTask,
                navigateAllTask = navigateAllTask,
                navigateEditTask = navigateEditTask,
                onSortTaskChanged = onSortTaskChanged,
                onTaskDelete = onTaskDelete,
                onTaskToggleCompletion = onTaskToggleCompletion,
                onShowMessageSnackBar = onShowMessageSnackBar,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    completedTasks: ImmutableList<Task>,
    incompleteTasks: ImmutableList<Task>,
    categories: ImmutableList<Category>,
    sleepTime: LocalTime,
    sortTask: SortTask,
    theme: Theme,
    buildVersion: String,
    navigateCalendar: () -> Unit,
    navigateSetting: () -> Unit,
    navigateAddTask: () -> Unit,
    navigateCompletedTask: (String) -> Unit,
    navigateIncompleteTask: (String) -> Unit,
    navigateThisWeekTask: (String) -> Unit,
    navigateAllTask: (String) -> Unit,
    navigateEditTask: (Long) -> Unit,
    onSortTaskChanged: (SortTask) -> Unit,
    onTaskDelete: (id: Long, uuid: String) -> Unit,
    onTaskToggleCompletion: (Long, Boolean) -> Unit,
    onShowMessageSnackBar: (message: String) -> Unit,
) {
    val translateX = 600f
    val leftTranslate = remember { Animatable(-translateX) }
    val rightTranslate = remember { Animatable(translateX) }

    LaunchedEffect(Unit) {
        launch {
            leftTranslate.animateTo(
                0f,
                tween(500)
            )
        }
        launch {
            rightTranslate.animateTo(
                0f,
                tween(500)
            )
        }
    }

    var isShowSortTaskDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                title = {
                    Text(
                        text = stringResource(id = DesignSystemR.string.app_name),
                        style = TodoTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                actions = {
                    IconButton(
                        onClick = navigateCalendar
                    ) {
                        Icon(
                            modifier = Modifier.size(21.dp),
                            imageVector = ImageVector.vectorResource(DesignSystemR.drawable.svg_calendar),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    IconButton(onClick = navigateSetting) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = ImageVector.vectorResource(DesignSystemR.drawable.svg_setting),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.surface,
                onClick = navigateAddTask,
            ) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    imageVector = ImageVector.vectorResource(DesignSystemR.drawable.svg_plus_small),
                    contentDescription = null
                )
            }
        }
    ) { paddingValues ->
        if (isShowSortTaskDialog) {
            SortTaskDialog(
                initSortTask = sortTask,
                onCloseClick = { isShowSortTaskDialog = false },
                onSelectClick = {
                    onSortTaskChanged(it)
                    isShowSortTaskDialog = false
                }
            )
        }

        Column(
            modifier = Modifier.padding(paddingValues),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    ),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TaskInfoCard(
                    modifier = Modifier.weight(1f)
                        .graphicsLayer {
                            translationX = leftTranslate.value
                        },
                    title = stringResource(DesignSystemR.string.completed),
                    icon = DesignSystemR.drawable.svg_completed,
                    content = "Today ${completedTasks.size} Tasks",
                    backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                    onClick = navigateCompletedTask,
                )
                TaskInfoCard(
                    modifier = Modifier.weight(1f)
                        .graphicsLayer {
                            translationX = rightTranslate.value
                        },
                    title = stringResource(DesignSystemR.string.incomplete),
                    icon = DesignSystemR.drawable.svg_incomplete,
                    content = "Today ${incompleteTasks.size} Tasks",
                    backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                    onClick = navigateIncompleteTask,
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    ),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TaskInfoCard(
                    modifier = Modifier.weight(1f)
                        .graphicsLayer {
                            translationX = leftTranslate.value
                        },
                    title = stringResource(DesignSystemR.string.this_week),
                    backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                    onClick = navigateThisWeekTask,
                )
                TaskInfoCard(
                    modifier = Modifier.weight(1f)
                        .graphicsLayer {
                            translationX = rightTranslate.value
                        },
                    title = stringResource(DesignSystemR.string.all),
                    backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                    onClick = navigateAllTask,
                )
            }

            if (incompleteTasks.isEmpty()) {
                EmptyContent(title = stringResource(DesignSystemR.string.no_tasks))
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = stringResource(DesignSystemR.string.today_tasks),
                        style = TodoTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )

                    IconButton(onClick = { isShowSortTaskDialog = true }) {
                        Icon(
                            modifier = Modifier.size(18.dp),
                            imageVector = ImageVector.vectorResource(DesignSystemR.drawable.svg_sort),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                val sortedTasks: List<Task> = remember(incompleteTasks, sortTask) {
                    incompleteTasks.sortedWith(
                        compareBy {
                            when (sortTask) {
                                SortTask.BY_CREATE_TIME_ASCENDING -> {
                                    it.id
                                }

                                SortTask.BY_CREATE_TIME_DESCENDING -> {
                                    -it.id
                                }

                                SortTask.BY_PRIORITY_ASCENDING -> {
                                    it.priority
                                }

                                SortTask.BY_PRIORITY_DESCENDING -> {
                                    -it.priority
                                }

                                SortTask.BY_TIME_ASCENDING -> {
                                    it.time.toSecondOfDay()
                                }

                                SortTask.BY_TIME_DESCENDING -> {
                                    -it.time.toSecondOfDay()
                                }
                            }
                        }
                    )
                }

                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 10.dp,
                        )
                ) {
                    itemsIndexed(
                        items = sortedTasks,
                        key = { _, task ->
                            task.id
                        }
                    ) { _, task ->
                        Box(
                            modifier = Modifier.animateItem(tween(500))
                        ) {
                            SwipeActionBox(
                                item = task,
                                onDeleteAction = {
                                    onTaskDelete(it.id, it.uuid)
                                    onShowMessageSnackBar("Task Deleted")
                                }
                            ) {
                                TaskCard(
                                    task = task,
                                    category = categories.filter { it.id == task.categoryId }
                                        .getOrNull(0),
                                    isAvailableSwipe = true,
                                    onTaskEdit = { taskId -> navigateEditTask(taskId) },
                                    onTaskToggleCompletion = { taskId, isCompleted ->
                                        onTaskToggleCompletion(taskId, isCompleted)
                                    },
                                    onTaskDelete = { taskId, uuid -> onTaskDelete(taskId, uuid) },
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    TodoTheme {
        val completedTasks = persistentListOf(
            Task(
                id = 7418,
                uuid = "cu",
                title = "purus",
                isCompleted = false,
                isRemind = true,
                time = LocalTime.now(),
                date = LocalDate.now(),
                memo = "memo",
                priority = 1
            )
        )
        HomeScreen(
            completedTasks = completedTasks,
            incompleteTasks = persistentListOf(),
            categories = persistentListOf(),
            sleepTime = LocalTime.now(),
            sortTask = SortTask.BY_TIME_ASCENDING,
            theme = Theme(ThemeType.SUN_RISE),
            buildVersion = "1.0.0",
            navigateCalendar = {},
            navigateSetting = {},
            navigateAddTask = {},
            navigateCompletedTask = {},
            navigateIncompleteTask = {},
            navigateThisWeekTask = {},
            navigateAllTask = {},
            navigateEditTask = {},
            onSortTaskChanged = {},
            onTaskDelete = { _, _ -> },
            onTaskToggleCompletion = { _, _ -> },
            onShowMessageSnackBar = {},
        )
    }
}