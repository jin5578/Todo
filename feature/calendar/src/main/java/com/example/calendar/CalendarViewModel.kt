package com.example.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calendar.model.CalendarUiState
import com.example.domain.usecase.DeleteTaskUseCase
import com.example.domain.usecase.GetCalendarDataUseCase
import com.example.domain.usecase.GetTaskByIdUseCase
import com.example.domain.usecase.UpdateTaskUseCase
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
class CalendarViewModel @Inject constructor(
    private val getCalendarDataUseCase: GetCalendarDataUseCase,
    private val getTaskByIdUseCase: GetTaskByIdUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
) : ViewModel() {
    private val _errorFlow: MutableSharedFlow<Throwable> = MutableSharedFlow()
    val errorFlow = _errorFlow.asSharedFlow()

    private val _uiState: MutableStateFlow<CalendarUiState> =
        MutableStateFlow(CalendarUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        fetchCalendar()
    }

    private fun fetchCalendar() {
        viewModelScope.launch {
            getCalendarDataUseCase().map { calendar ->
                CalendarUiState.Success(
                    tasks = calendar.tasks.toPersistentList(),
                    categories = calendar.categories.toPersistentList(),
                    sortTask = calendar.calendarSystem.sortTask
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