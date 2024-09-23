package com.example.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetCompletedTasksUseCase
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
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val getCompletedTasksUseCase: GetCompletedTasksUseCase,
) : ViewModel() {
    private val _errorFlow: MutableSharedFlow<Throwable> = MutableSharedFlow()
    val errorFlow = _errorFlow.asSharedFlow()

    private val _uiState: MutableStateFlow<TasksUiState> =
        MutableStateFlow(TasksUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun fetchTasks(title: String) {
        when (title) {
            "Completed" -> {
                fetchCompletedTasks()
            }

            "Incomplete" -> {

            }

            "This week" -> {

            }

            else -> {

            }
        }
    }

    private fun fetchCompletedTasks() {
        viewModelScope.launch {
            getCompletedTasksUseCase().map { tasks ->
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
}