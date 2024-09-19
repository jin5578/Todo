package com.example.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.DeleteTaskUseCase
import com.example.domain.usecase.GetHomeDataUseCase
import com.example.domain.usecase.GetTaskByIdUseCase
import com.example.domain.usecase.UpdateSortTaskUseCase
import com.example.domain.usecase.UpdateTaskUseCase
import com.example.home.model.HomeUiState
import com.example.model.SortTask
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
class HomeViewModel @Inject constructor(
    private val getHomeDataUseCase: GetHomeDataUseCase,
    private val updateSortTaskUseCase: UpdateSortTaskUseCase,
    private val getTaskByIdUseCase: GetTaskByIdUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
) : ViewModel() {
    private val _errorFlow: MutableSharedFlow<Throwable> = MutableSharedFlow()
    val errorFlow = _errorFlow.asSharedFlow()

    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        fetchHome()
    }

    private fun fetchHome() {
        viewModelScope.launch {
            getHomeDataUseCase().map { home ->
                val completedTasks = home.tasks.filter { task ->
                    task.isCompleted
                }.toPersistentList()
                val incompleteTasks = home.tasks.filter { task ->
                    !task.isCompleted
                }.toPersistentList()

                val homeSystem = home.homeSystem

                HomeUiState.Success(
                    completedTasks = completedTasks,
                    incompleteTasks = incompleteTasks,
                    sleepTime = homeSystem.sleepTime,
                    sortTask = homeSystem.sortTask,
                    theme = homeSystem.theme,
                    buildVersion = homeSystem.buildVersion
                )
            }.catch { throwable ->
                _errorFlow.emit(throwable)
            }.collect {
                _uiState.value = it
            }
        }
    }

    fun updateSortTask(sortTask: SortTask) {
        val state = _uiState.value
        if (state !is HomeUiState.Success) {
            return
        }

        _uiState.value = state.copy(
            sortTask = sortTask
        )

        viewModelScope.launch {
            updateSortTaskUseCase(sortTask)
        }
    }

    fun deleteTask(taskId: Long) {
        viewModelScope.launch {
            val task = getTaskByIdUseCase(taskId)
            deleteTaskUseCase(task)
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
}