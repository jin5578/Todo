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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.design_system.component.SortTaskDialog
import com.example.design_system.theme.Blue
import com.example.design_system.theme.BurntSienna
import com.example.design_system.theme.CoralSand
import com.example.design_system.theme.PeachOrange
import com.example.design_system.theme.RoseTaupe
import com.example.design_system.theme.TodoTheme
import com.example.home.component.EmptyTask
import com.example.home.component.HomeLoading
import com.example.home.component.NavigationDrawer
import com.example.home.component.SwipeActionBox
import com.example.home.component.TaskCard
import com.example.home.component.TaskInfoCard
import com.example.home.model.HomeUiState
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

@Composable
internal fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToCalendarScreen: () -> Unit,
    navigateToAddTaskScreen: () -> Unit,
    navigateToCompletedTaskScreen: () -> Unit,
    navigateToIncompleteTaskScreen: () -> Unit,
    navigateToThisWeekTaskScreen: () -> Unit,
    navigateToAllTaskScreen: () -> Unit,
    navigateToEditTaskScreen: (Long) -> Unit,
    onShowErrorSnackbar: (throwable: Throwable?) -> Unit,
    onShowMessageSnackBar: (message: String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.errorFlow.collectLatest { throwable ->
            onShowErrorSnackbar(throwable)
        }
    }

    HomeContent(
        uiState = uiState,
        navigateToCalendarScreen = navigateToCalendarScreen,
        navigateToAddTaskScreen = navigateToAddTaskScreen,
        navigateToCompletedTaskScreen = navigateToCompletedTaskScreen,
        navigateToIncompleteTaskScreen = navigateToIncompleteTaskScreen,
        navigateToThisWeekTaskScreen = navigateToThisWeekTaskScreen,
        navigateToAllTaskScreen = navigateToAllTaskScreen,
        navigateToEditTaskScreen = navigateToEditTaskScreen,
        onSortTaskChanged = viewModel::updateSortTask,
        onTaskDelete = viewModel::deleteTask,
        onTaskComplete = { taskId ->
            viewModel.toggleTaskCompletion(
                taskId = taskId,
                isCompleted = true
            )
        },
        onShowMessageSnackBar = onShowMessageSnackBar,
    )
}

@Composable
private fun HomeContent(
    uiState: HomeUiState,
    navigateToCalendarScreen: () -> Unit,
    navigateToAddTaskScreen: () -> Unit,
    navigateToCompletedTaskScreen: () -> Unit,
    navigateToIncompleteTaskScreen: () -> Unit,
    navigateToThisWeekTaskScreen: () -> Unit,
    navigateToAllTaskScreen: () -> Unit,
    navigateToEditTaskScreen: (Long) -> Unit,
    onSortTaskChanged: (SortTask) -> Unit,
    onTaskDelete: (Long) -> Unit,
    onTaskComplete: (Long) -> Unit,
    onShowMessageSnackBar: (message: String) -> Unit,
) {
    when (uiState) {
        is HomeUiState.Loading -> {
            HomeLoading()
        }

        is HomeUiState.Success -> {
            HomeScreen(
                completedTasks = uiState.completedTasks,
                incompleteTasks = uiState.incompleteTasks,
                sleepTime = uiState.sleepTime,
                sortTask = uiState.sortTask,
                theme = uiState.theme,
                buildVersion = uiState.buildVersion,
                navigateToCalendarScreen = navigateToCalendarScreen,
                navigateToAddTaskScreen = navigateToAddTaskScreen,
                navigateToCompletedTaskScreen = navigateToCompletedTaskScreen,
                navigateToIncompleteTaskScreen = navigateToIncompleteTaskScreen,
                navigateToThisWeekTaskScreen = navigateToThisWeekTaskScreen,
                navigateToAllTaskScreen = navigateToAllTaskScreen,
                navigateToEditTaskScreen = navigateToEditTaskScreen,
                onSortTaskChanged = onSortTaskChanged,
                onTaskDelete = onTaskDelete,
                onTaskComplete = onTaskComplete,
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
    sleepTime: LocalTime,
    sortTask: SortTask,
    theme: Theme,
    buildVersion: String,
    navigateToCalendarScreen: () -> Unit,
    navigateToAddTaskScreen: () -> Unit,
    navigateToCompletedTaskScreen: () -> Unit,
    navigateToIncompleteTaskScreen: () -> Unit,
    navigateToThisWeekTaskScreen: () -> Unit,
    navigateToAllTaskScreen: () -> Unit,
    navigateToEditTaskScreen: (Long) -> Unit,
    onSortTaskChanged: (SortTask) -> Unit,
    onTaskDelete: (Long) -> Unit,
    onTaskComplete: (Long) -> Unit,
    onShowMessageSnackBar: (message: String) -> Unit,
) {
    val translateX = 600f
    val leftTranslate = remember { Animatable(-translateX) }
    val rightTranslate = remember { Animatable(translateX) }

    LaunchedEffect(Unit) {
        launch {
            leftTranslate.animateTo(
                0f,
                tween(1000)
            )
        }
        launch {
            rightTranslate.animateTo(
                0f,
                tween(1000)
            )
        }
    }

    var isShowSortTaskDialog by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(drawerContainerColor = MaterialTheme.colorScheme.primary) {
                NavigationDrawer(buildVersion = buildVersion)
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    ),
                    title = {
                        Text(
                            text = stringResource(id = R.string.app_name),
                            style = TodoTheme.typography.headlineLarge
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = null
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = navigateToCalendarScreen,
                        ) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = null
                            )
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    containerColor = Blue,
                    contentColor = MaterialTheme.colorScheme.secondary,
                    onClick = navigateToAddTaskScreen,
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null
                    )
                }
            }
        ) { innerPadding ->
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

            Column(modifier = Modifier.padding(innerPadding)) {
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
                        title = stringResource(R.string.completed),
                        icon = R.drawable.svg_task_list,
                        content = "${completedTasks.size} Tasks",
                        backgroundColor = PeachOrange,
                        onClick = navigateToCompletedTaskScreen,
                    )
                    TaskInfoCard(
                        modifier = Modifier.weight(1f)
                            .graphicsLayer {
                                translationX = rightTranslate.value
                            },
                        title = stringResource(R.string.incomplete),
                        icon = R.drawable.svg_task_list,
                        content = "${incompleteTasks.size} Tasks",
                        backgroundColor = BurntSienna,
                        onClick = navigateToIncompleteTaskScreen,
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
                        title = stringResource(R.string.this_week),
                        icon = R.drawable.svg_task_list,
                        content = "${completedTasks.size} Tasks",
                        backgroundColor = RoseTaupe,
                        onClick = navigateToThisWeekTaskScreen,
                    )
                    TaskInfoCard(
                        modifier = Modifier.weight(1f)
                            .graphicsLayer {
                                translationX = rightTranslate.value
                            },
                        title = stringResource(R.string.all),
                        icon = R.drawable.svg_task_list,
                        content = "${incompleteTasks.size} Tasks",
                        backgroundColor = CoralSand,
                        onClick = navigateToAllTaskScreen,
                    )
                }

                if (incompleteTasks.isEmpty()) {
                    EmptyTask()
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = stringResource(R.string.today_tasks),
                            style = TodoTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onPrimary,
                        )

                        IconButton(onClick = { isShowSortTaskDialog = true }) {
                            Icon(
                                imageVector = Icons.Default.FilterList,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary
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

                                    SortTask.BY_START_TIME_ASCENDING -> {
                                        it.startTime.toSecondOfDay()
                                    }

                                    SortTask.BY_START_TIME_DESCENDING -> {
                                        -it.startTime.toSecondOfDay()
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
                        ) { index, task ->
                            Box(
                                modifier = Modifier.animateItem(tween(500))
                            ) {
                                SwipeActionBox(
                                    item = task,
                                    onDeleteAction = {
                                        onTaskDelete(it.id)
                                        onShowMessageSnackBar("Task Deleted")
                                    }
                                ) {
                                    TaskCard(
                                        task = task,
                                        animDelay = index * 100,
                                        onTaskEdit = { taskId -> navigateToEditTaskScreen(taskId) },
                                        onTaskComplete = { taskId -> onTaskComplete(taskId) },
                                        onTaskDelete = { taskId -> onTaskDelete(taskId) },
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
                startTime = LocalTime.now(),
                endTime = LocalTime.now(),
                date = LocalDate.now(),
                priority = 1
            )
        )
        HomeScreen(
            completedTasks = completedTasks,
            incompleteTasks = persistentListOf(),
            sleepTime = LocalTime.now(),
            sortTask = SortTask.BY_START_TIME_ASCENDING,
            theme = Theme(ThemeType.LIGHT),
            buildVersion = "1.0.0",
            navigateToCalendarScreen = {},
            navigateToAddTaskScreen = {},
            navigateToCompletedTaskScreen = {},
            navigateToIncompleteTaskScreen = {},
            navigateToThisWeekTaskScreen = {},
            navigateToAllTaskScreen = {},
            navigateToEditTaskScreen = {},
            onSortTaskChanged = {},
            onTaskDelete = {},
            onTaskComplete = {},
            onShowMessageSnackBar = {},
        )
    }
}