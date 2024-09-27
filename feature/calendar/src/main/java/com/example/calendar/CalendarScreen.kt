package com.example.calendar

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.calendar.model.CalendarUiState
import com.example.design_system.component.EmptyContent
import com.example.design_system.component.Loading
import com.example.design_system.component.TaskCard
import com.example.design_system.component.WeekendDay
import com.example.design_system.theme.TodoTheme
import com.example.model.Task
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import com.example.design_system.R as DesignSystemR

@Composable
internal fun CalendarRoute(
    viewModel: CalendarViewModel = hiltViewModel(),
    navigateAddTask: () -> Unit,
    navigateEditTask: (Long) -> Unit,
    popBackStack: () -> Unit,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
    onShowMessageSnackBar: (message: String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.errorFlow.collectLatest { throwable ->
            onShowErrorSnackBar(throwable)
        }
    }

    CalendarContent(
        uiState = uiState,
        navigateAddTask = navigateAddTask,
        navigateEditTask = navigateEditTask,
        popBackStack = popBackStack,
        onTaskToggleCompletion = { taskId, isChecked ->
            viewModel.toggleTaskCompletion(
                taskId = taskId,
                isCompleted = isChecked
            )
        },
        onTaskDelete = viewModel::deleteTask
    )
}

@Composable
private fun CalendarContent(
    uiState: CalendarUiState,
    navigateAddTask: () -> Unit,
    navigateEditTask: (Long) -> Unit,
    popBackStack: () -> Unit,
    onTaskToggleCompletion: (Long, Boolean) -> Unit,
    onTaskDelete: (Long) -> Unit,
) {
    when (uiState) {
        is CalendarUiState.Loading -> {
            Loading()
        }

        is CalendarUiState.Success -> {
            CalendarScreen(
                tasks = uiState.tasks,
                navigateAddTask = navigateAddTask,
                navigateEditTask = navigateEditTask,
                popBackStack = popBackStack,
                onTaskToggleCompletion = onTaskToggleCompletion,
                onTaskDelete = onTaskDelete
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CalendarScreen(
    tasks: ImmutableList<Task> = persistentListOf(),
    navigateAddTask: () -> Unit,
    navigateEditTask: (Long) -> Unit,
    popBackStack: () -> Unit,
    onTaskToggleCompletion: (Long, Boolean) -> Unit,
    onTaskDelete: (Long) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    val currentMonth = remember { YearMonth.now() }
    var currentMonthTitle by remember { mutableStateOf(currentMonth.month) }
    val currentDate = remember { LocalDate.now() }
    var selectedDay by remember { mutableStateOf(currentDate) }

    val weekCalendarState = rememberWeekCalendarState(
        startDate = currentDate.minusDays(100),
        endDate = currentDate.plusDays(100),
        firstDayOfWeek = firstDayOfWeekFromLocale()
    )

    val dateFormat = DateTimeFormatter.ofPattern("dd")

    currentMonthTitle = weekCalendarState.firstVisibleWeek.days[0].date.month

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                ),
                title = {
                    Text(
                        text = currentMonthTitle.getDisplayName(
                            TextStyle.FULL,
                            Locale.US
                        ),
                        style = TodoTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                navigationIcon = {
                    IconButton(onClick = popBackStack) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = ImageVector.vectorResource(DesignSystemR.drawable.svg_arrow_left),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                actions = {
                    Row {
                        Box(
                            modifier = Modifier.clickable {
                                selectedDay = currentDate
                                coroutineScope.launch {
                                    weekCalendarState.animateScrollToWeek(LocalDate.now())
                                }
                            }
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = RoundedCornerShape(8.dp)
                                ),
                        ) {
                            Text(
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp),
                                text = currentDate.format(dateFormat),
                                style = TodoTheme.typography.infoTextStyle.copy(
                                    fontSize = 14.sp
                                )
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
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
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(paddingValues)
        ) {
            WeekCalendar(
                modifier = Modifier.padding(horizontal = 10.dp),
                state = weekCalendarState,
                dayContent = { day ->
                    WeekendDay(
                        day = day,
                        isSelected = selectedDay == day.date,
                        isVisibleIndicator = tasks.any { it.date == day.date },
                        onClick = { selectedDay = it }
                    )
                }
            )
            val selectedDayTasks = tasks.filter { it.date == selectedDay }.toPersistentList()
            if (selectedDayTasks.isEmpty()) {
                EmptyContent(title = stringResource(DesignSystemR.string.no_tasks))
            } else {
                Spacer(modifier = Modifier.height(10.dp))
                LazyColumn(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 0.dp)
                ) {
                    itemsIndexed(
                        items = selectedDayTasks,
                        key = { _, task ->
                            task.id
                        }
                    ) { _, task ->
                        TaskCard(
                            task = task,
                            isAvailableSwipe = true,
                            onTaskEdit = { taskId -> navigateEditTask(taskId) },
                            onTaskToggleCompletion = { taskId, isCompleted ->
                                onTaskToggleCompletion(taskId, isCompleted)
                            },
                            onTaskDelete = { taskId -> onTaskDelete(taskId) },
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun CalendarScreenPreview() {
    TodoTheme {
        CalendarScreen(
            tasks = persistentListOf(),
            navigateAddTask = {},
            navigateEditTask = {},
            popBackStack = {},
            onTaskToggleCompletion = { _, _ -> },
            onTaskDelete = {}
        )
    }
}