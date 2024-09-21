package com.example.add_task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.add_task.component.AddTaskDate
import com.example.add_task.component.AddTaskLoading
import com.example.add_task.component.AddTaskTitle
import com.example.add_task.model.AddTaskUiState
import com.example.design_system.component.DatePickerDialog
import com.example.design_system.theme.TodoTheme
import com.example.design_system.theme.priorityColors
import com.example.model.Priority
import com.example.model.TimePicker
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.job
import java.time.LocalDate

@Composable
internal fun AddTaskRoute(
    viewModel: AddTaskViewModel = hiltViewModel(),
    date: LocalDate,
    popBackStack: () -> Unit,
    onShowErrorSnackbar: (throwable: Throwable?) -> Unit,
    onShowMessageSnackBar: (message: String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    LaunchedEffect(date) {
        viewModel.fetchAddTask(date)
    }

    LaunchedEffect(true) {
        viewModel.errorFlow.collectLatest { throwable ->
            onShowErrorSnackbar(throwable)
        }
    }

    AddTaskContent(
        uiState = uiState,
        popBackStack = popBackStack,
        onShowMessageSnackBar = onShowMessageSnackBar
    )
}

@Composable
private fun AddTaskContent(
    uiState: AddTaskUiState,
    popBackStack: () -> Unit,
    onShowMessageSnackBar: (message: String) -> Unit,
) {
    when (uiState) {
        is AddTaskUiState.Loading -> {
            AddTaskLoading()
        }

        is AddTaskUiState.Success -> {
            AddTaskScreen(
                date = uiState.date,
                timePicker = uiState.timePicker,
                popBackStack = popBackStack,
                onShowMessageSnackBar = onShowMessageSnackBar
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddTaskScreen(
    date: LocalDate,
    timePicker: TimePicker,
    popBackStack: () -> Unit,
    onShowMessageSnackBar: (message: String) -> Unit,
) {
    val focusRequester = FocusRequester()
    val scrollState = rememberScrollState()

    var isShowDatePickerDialog by remember { mutableStateOf(false) }

    var taskPriority by remember { mutableStateOf(Priority.LOW) }
    var taskText by remember { mutableStateOf("") }
    var taskDate by remember { mutableStateOf(date) }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                title = {
                    Text(
                        text = stringResource(R.string.add_task),
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
                defaultDay = taskDate,
                onClose = { day ->
                    taskDate = day
                    isShowDatePickerDialog = false
                }
            )
        }

        LaunchedEffect(
            key1 = true,
            block = {
                coroutineContext.job.invokeOnCompletion {
                    focusRequester.requestFocus()
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
                AddTaskTitle(
                    focusRequester = focusRequester,
                    backgroundColor = priorityColors[taskPriority.ordinal],
                    taskText = taskText,
                    onValueChange = { taskText = it }
                )
                AddTaskDate(
                    date = taskDate,
                    onDateChange = { taskDate = it },
                    onShowDatePickerDialog = { isShowDatePickerDialog = true }
                )
            }
        }
    }
}