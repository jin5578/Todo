package com.example.edit_task.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.example.model.Category
import com.example.model.Task
import com.example.model.TimePicker
import kotlinx.collections.immutable.ImmutableList

@Stable
sealed interface EditTaskUiState {
    @Immutable
    data object Loading : EditTaskUiState

    @Immutable
    data class Success(
        val task: Task,
        val timePicker: TimePicker,
        val categories: ImmutableList<Category>
    ) : EditTaskUiState
}