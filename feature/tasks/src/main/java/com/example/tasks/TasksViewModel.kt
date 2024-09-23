package com.example.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.DeleteTaskUseCase
import com.example.domain.usecase.GetAllTaskUseCase
import com.example.domain.usecase.GetTaskByIdUseCase
import com.example.domain.usecase.GetTasksByDateRangeUseCase
import com.example.domain.usecase.GetTasksByStateUseCase
import com.example.domain.usecase.UpdateTaskUseCase
import com.example.tasks.model.TasksUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val getTasksByStateUseCase: GetTasksByStateUseCase,
    private val getTasksByDateRangeUseCase: GetTasksByDateRangeUseCase,
    private val getAllTaskUseCase: GetAllTaskUseCase,
    private val getTaskByIdUseCase: GetTaskByIdUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
) : ViewModel() {
    private val _errorFlow: MutableSharedFlow<Throwable> = MutableSharedFlow()
    val errorFlow = _errorFlow.asSharedFlow()

    private val _uiState: MutableStateFlow<TasksUiState> =
        MutableStateFlow(TasksUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun fetchTasks(title: String) {
        when (title) {
            "Completed" -> fetchTasksByState(true)
            "Incomplete" -> fetchTasksByState(false)
            "This week" -> {
                val currentDate = LocalDate.now()
                val fromDate = currentDate.with(DayOfWeek.MONDAY)
                val toDate = currentDate.with(DayOfWeek.SUNDAY)

                fetchTasksByDateRange(
                    fromDate = fromDate,
                    toDate = toDate
                )
            }

            else -> fetchAllTasks()
        }
    }

    private fun fetchTasksByState(isCompleted: Boolean) {
        viewModelScope.launch {
            getTasksByStateUseCase(isCompleted).map { tasks ->
                TasksUiState.Success(
                    tasks = tasks.toPersistentList()
                )
            }.catch { throwable ->
                _errorFlow.emit(throwable)
            }.collect {
                _uiState.value = it
            }
        }
    }

    private fun fetchTasksByDateRange(fromDate: LocalDate, toDate: LocalDate) {
        viewModelScope.launch {
            getTasksByDateRangeUseCase(fromDate = fromDate, toDate = toDate).map { tasks ->
                TasksUiState.Success(
                    tasks = tasks.toPersistentList()
                )
            }.catch { throwable ->
                _errorFlow.emit(throwable)
            }.collect {
                _uiState.value = it
            }
        }
    }

    private fun fetchAllTasks() {
        viewModelScope.launch {
            getAllTaskUseCase().map { tasks ->
                TasksUiState.Success(
                    tasks = tasks.toPersistentList()
                )
            }.catch { throwable ->
                _errorFlow.emit(throwable)
            }.collect {
                _uiState.value = it
            }
        }
    }

    fun toggleTaskCompletion(
        taskId: Long,
        isCompleted: Boolean
    ) {
        viewModelScope.launch {
            val task = getTaskByIdUseCase(taskId).copy(isCompleted = isCompleted)
            updateTaskUseCase(task)
        }
    }

    fun deleteTask(taskId: Long) {
        viewModelScope.launch {
            val task = getTaskByIdUseCase(taskId)
            deleteTaskUseCase(task)
        }
    }
}