package com.example.add_task.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.example.model.Category
import com.example.model.TimePicker
import kotlinx.collections.immutable.ImmutableList
import java.time.LocalDate

@Stable
sealed interface AddTaskUiState {
    @Immutable
    data object Loading : AddTaskUiState

    @Immutable
    data class Success(
        val date: LocalDate,
        val timePicker: TimePicker,
        val categories: ImmutableList<Category>
    ) : AddTaskUiState
}