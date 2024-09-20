package com.example.add_task.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.example.model.TimePicker
import java.time.LocalDate

@Stable
sealed interface AddTaskUiState {
    @Immutable
    data object Loading : AddTaskUiState

    @Immutable
    data class Success(
        val date: LocalDate,
        val timePicker: TimePicker,
    ) : AddTaskUiState
}