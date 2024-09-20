package com.example.add_task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.add_task.model.AddTaskUiState
import com.example.domain.usecase.GetAddTaskDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val getAddTaskDataUseCase: GetAddTaskDataUseCase
) : ViewModel() {
    private val _errorFlow: MutableSharedFlow<Throwable> = MutableSharedFlow<Throwable>()
    val errorFlow = _errorFlow.asSharedFlow()

    private val _uiState: MutableStateFlow<AddTaskUiState> =
        MutableStateFlow(AddTaskUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun fetchAddTask(date: LocalDate) {
        viewModelScope.launch {
            getAddTaskDataUseCase(date).map { addTask ->
                val addTaskSetting = addTask.addTaskSystem
                AddTaskUiState.Success(
                    date = date,
                    timePicker = addTaskSetting.timePicker
                )
            }.catch { throwable ->
                _errorFlow.emit(throwable)
            }.collect {
                _uiState.value = it
            }
        }
    }
}