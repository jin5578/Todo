package com.example.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetTasksByDateRange
import com.example.domain.usecase.GetTasksByState
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
import timber.log.Timber
import java.time.DayOfWeek
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val getTasksByState: GetTasksByState,
    private val getTasksByDateRange: GetTasksByDateRange,
) : ViewModel() {
    private val _errorFlow: MutableSharedFlow<Throwable> = MutableSharedFlow()
    val errorFlow = _errorFlow.asSharedFlow()

    private val _uiState: MutableStateFlow<TasksUiState> =
        MutableStateFlow(TasksUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun fetchTasks(title: String) {
        when (title) {
            "Completed" -> {
                fetchTasksByState(true)
            }

            "Incomplete" -> {
                fetchTasksByState(false)
            }

            "This week" -> {
                val currentDate = LocalDate.now()
                val fromDate = currentDate.with(DayOfWeek.MONDAY)
                val toDate = currentDate.with(DayOfWeek.SUNDAY)

                fetchTasksByDateRange(
                    from = fromDate,
                    to = toDate
                )
            }

            else -> {

            }
        }
    }

    private fun fetchTasksByState(isCompleted: Boolean) {
        viewModelScope.launch {
            getTasksByState(isCompleted).map { tasks ->
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

    private fun fetchTasksByDateRange(from: LocalDate, to: LocalDate) {
        viewModelScope.launch {
            getTasksByDateRange(from = from, to = to).map { tasks ->
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

        }
    }
}