package com.example.calendar.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.example.model.Category
import com.example.model.SortTask
import com.example.model.Task
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Stable
sealed interface CalendarUiState {
    @Immutable
    data object Loading : CalendarUiState

    @Immutable
    data class Success(
        val tasks: ImmutableList<Task> = persistentListOf(),
        val categories: ImmutableList<Category> = persistentListOf(),
        val sortTask: SortTask,
    ) : CalendarUiState
}