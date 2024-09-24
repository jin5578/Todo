package com.example.edit_task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetEditTaskDataUseCase
import com.example.domain.usecase.UpdateTaskUseCase
import com.example.edit_task.model.EditTaskUiEffect
import com.example.edit_task.model.EditTaskUiState
import com.example.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditTaskViewModel @Inject constructor(
    private val getEditTaskDataUseCase: GetEditTaskDataUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
) : ViewModel() {
    private val _errorFlow: MutableSharedFlow<Throwable> = MutableSharedFlow()
    val errorFlow = _errorFlow.asSharedFlow()

    private val _uiState: MutableStateFlow<EditTaskUiState> =
        MutableStateFlow(EditTaskUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _uiEffect: MutableStateFlow<EditTaskUiEffect> =
        MutableStateFlow(EditTaskUiEffect.Idle)
    val uiEffect = _uiEffect.asStateFlow()

    fun fetchEditTask(taskId: Long) {
        viewModelScope.launch {
            getEditTaskDataUseCase(taskId).map { editTask ->
                val editTaskSetting = editTask.editTaskSystem
                EditTaskUiState.Success(
                    task = editTask.task,
                    timePicker = editTaskSetting.timePicker
                )
            }.catch { throwable ->
                _errorFlow.emit(throwable)
            }.collect {
                _uiState.value = it
            }
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            updateTaskUseCase(task)
            _uiEffect.value =
                EditTaskUiEffect.SuccessUpdateTask(SUCCESSFULLY_UPDATED_THE_SCHEDULE)
        }
    }

    companion object {
        private const val SUCCESSFULLY_UPDATED_THE_SCHEDULE = "Successfully updated the schedule"
    }
}