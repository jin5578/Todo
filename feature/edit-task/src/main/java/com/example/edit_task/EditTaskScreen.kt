package com.example.edit_task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.design_system.component.DatePickerDialog
import com.example.design_system.component.Loading
import com.example.design_system.component.TimePickerDialog
import com.example.design_system.component.input.InputTaskDate
import com.example.design_system.component.input.InputTaskMemo
import com.example.design_system.component.input.InputTaskPriority
import com.example.design_system.component.input.InputTaskTime
import com.example.design_system.component.input.InputTaskTitle
import com.example.design_system.theme.TodoTheme
import com.example.design_system.theme.priorityColors
import com.example.edit_task.model.EditTaskUiEffect
import com.example.edit_task.model.EditTaskUiState
import com.example.model.Priority
import com.example.model.Task
import com.example.model.TimePicker
import com.example.utils.checkValidTask
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.job

@Composable
internal fun EditTaskRoute(
    viewModel: EditTaskViewModel = hiltViewModel(),
    taskId: Long,
    popBackStack: () -> Unit,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
    onShowMessageSnackBar: (message: String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val uiEffect by viewModel.uiEffect.collectAsStateWithLifecycle()

    LaunchedEffect(taskId) {
        viewModel.fetchEditTask(taskId)
    }

    LaunchedEffect(true) {
        viewModel.errorFlow.collectLatest { throwable ->
            onShowErrorSnackBar(throwable)
        }
    }

    LaunchedEffect(uiEffect) {
        if (uiEffect is EditTaskUiEffect.SuccessUpdateTask) {
            val message = (uiEffect as EditTaskUiEffect.SuccessUpdateTask).message
            onShowMessageSnackBar(message)
            popBackStack()
        }
    }

    EditTaskContent(
        uiState = uiState,
        popBackStack = popBackStack,
        onUpdateTaskClick = viewModel::updateTask,
        onShowMessageSnackBar = onShowMessageSnackBar
    )
}

@Composable
private fun EditTaskContent(
    uiState: EditTaskUiState,
    popBackStack: () -> Unit,
    onUpdateTaskClick: (Task) -> Unit,
    onShowMessageSnackBar: (message: String) -> Unit,
) {
    when (uiState) {
        is EditTaskUiState.Loading -> {
            Loading()
        }

        is EditTaskUiState.Success -> {
            EditTaskScreen(
                task = uiState.task,
                timePicker = uiState.timePicker,
                popBackStack = popBackStack,
                onUpdateTaskClick = onUpdateTaskClick,
                onShowMessageSnackBar = onShowMessageSnackBar
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditTaskScreen(
    task: Task,
    timePicker: TimePicker,
    popBackStack: () -> Unit,
    onUpdateTaskClick: (Task) -> Unit,
    onShowMessageSnackBar: (message: String) -> Unit,
) {
    val titleFocusRequester = FocusRequester()
    val memoFocusRequester = FocusRequester()

    val scrollState = rememberScrollState()

    var isShowDatePickerDialog by remember { mutableStateOf(false) }
    var isShowStartTimePickerDialog by remember { mutableStateOf(false) }
    var isShowEndTimePickerDialog by remember { mutableStateOf(false) }

    var taskTitle by remember { mutableStateOf(task.title) }
    var taskDate by remember { mutableStateOf(task.date) }
    var taskStartTime by remember { mutableStateOf(task.startTime) }
    var taskEndTime by remember { mutableStateOf(task.endTime) }
    var taskMemo by remember { mutableStateOf(task.memo) }
    var taskPriority by remember {
        mutableStateOf(Priority.entries.getOrNull(task.priority) ?: Priority.LOW)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                title = {
                    Text(
                        text = stringResource(R.string.edit_task),
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
        if (isShowDatePickerDialog) {
            DatePickerDialog(
                initDay = taskDate,
                onClose = { day ->
                    taskDate = day
                    isShowDatePickerDialog = false
                },
                onShowMessageSnackBar = onShowMessageSnackBar
            )
        }

        if (isShowStartTimePickerDialog) {
            TimePickerDialog(
                initTime = taskStartTime,
                onClose = {
                    taskStartTime = it
                    taskEndTime = taskStartTime.plusMinutes(30)
                    isShowStartTimePickerDialog = false
                }
            )
        }

        if (isShowEndTimePickerDialog) {
            TimePickerDialog(
                initTime = taskEndTime,
                onClose = {
                    taskEndTime = it
                    if (taskEndTime < taskStartTime) {
                        taskStartTime = taskEndTime.minusMinutes(30)
                    }
                    isShowEndTimePickerDialog = false
                }
            )
        }

        LaunchedEffect(
            key1 = true,
            block = {
                coroutineContext.job.invokeOnCompletion {
                    titleFocusRequester.requestFocus()
                }
            }
        )

        Column(
            modifier = Modifier.fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .background(MaterialTheme.colorScheme.surface),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(30.dp),
            ) {
                InputTaskTitle(
                    focusRequester = titleFocusRequester,
                    backgroundColor = priorityColors[taskPriority.ordinal],
                    taskTitle = taskTitle,
                    onValueChange = { taskTitle = it }
                )
                InputTaskDate(
                    date = taskDate,
                    onDateChange = { taskDate = it },
                    onShowDatePickerDialog = { isShowDatePickerDialog = true }
                )
                InputTaskTime(
                    timePicker = timePicker,
                    startTime = taskStartTime,
                    endTime = taskEndTime,
                    onSelectStartTime = {
                        taskStartTime = it
                        if (taskEndTime > taskStartTime) {
                            taskEndTime = taskStartTime.plusMinutes(30)
                        }
                    },
                    onSelectEndTime = {
                        taskEndTime = it
                        if (taskEndTime < taskStartTime) {
                            taskStartTime = taskEndTime.minusMinutes(30)
                        }
                    },
                    onShowStartTimePickerDialog = { isShowStartTimePickerDialog = true },
                    onShowEndTimePickerDialog = { isShowEndTimePickerDialog = true },
                )
                InputTaskMemo(
                    focusRequester = memoFocusRequester,
                    taskMemo = taskMemo,
                    onValueChange = { taskMemo = it }
                )
                InputTaskPriority(
                    initPriority = taskPriority,
                    onSelect = { taskPriority = it }
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        val updateTask = Task(
                            id = task.id,
                            uuid = task.uuid,
                            title = taskTitle.trim(),
                            isCompleted = task.isCompleted,
                            startTime = taskStartTime,
                            endTime = taskEndTime,
                            date = taskDate,
                            memo = taskMemo,
                            priority = taskPriority.ordinal
                        )

                        val (isValid, errorMessage) = checkValidTask(
                            task = updateTask,
                        )

                        if (isValid) {
                            onUpdateTaskClick(updateTask)
                        } else {
                            onShowMessageSnackBar(errorMessage)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                    ),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = stringResource(R.string.edit_task),
                        style = TodoTheme.typography.headlineSmall,
                    )
                }
            }
        }
    }
}