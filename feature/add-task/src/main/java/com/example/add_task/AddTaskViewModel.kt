package com.example.add_task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.add_task.model.AddTaskUiEffect
import com.example.add_task.model.AddTaskUiState
import com.example.domain.usecase.GetAddTaskDataUseCase
import com.example.domain.usecase.InsertTaskUseCase
import com.example.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
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
    private val getAddTaskDataUseCase: GetAddTaskDataUseCase,
    private val insertTaskUseCase: InsertTaskUseCase,
) : ViewModel() {
    private val _errorFlow: MutableSharedFlow<Throwable> = MutableSharedFlow()
    val errorFlow = _errorFlow.asSharedFlow()

    private val _uiState: MutableStateFlow<AddTaskUiState> =
        MutableStateFlow(AddTaskUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _uiEffect: MutableStateFlow<AddTaskUiEffect> =
        MutableStateFlow(AddTaskUiEffect.Idle)
    val uiEffect = _uiEffect.asStateFlow()

    fun fetchAddTask(date: LocalDate) {
        viewModelScope.launch {
            getAddTaskDataUseCase(date).map { addTask ->
                val addTaskSetting = addTask.addTaskSystem
                AddTaskUiState.Success(
                    date = date,
                    timePicker = addTaskSetting.timePicker,
                    categories = addTask.categories.toPersistentList()
                )
            }.catch { throwable ->
                _errorFlow.emit(throwable)
            }.collect {
                _uiState.value = it
            }
        }
    }

    fun insertTask(task: Task) {
        viewModelScope.launch {
            insertTaskUseCase(task)
            _uiEffect.value =
                AddTaskUiEffect.SuccessInsertTask(SUCCESSFULLY_ADDED_THE_SCHEDULE)
        }
    }

    companion object {
        private const val SUCCESSFULLY_ADDED_THE_SCHEDULE = "Successfully added the schedule"
    }
}