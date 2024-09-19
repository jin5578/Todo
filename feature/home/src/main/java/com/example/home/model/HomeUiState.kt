package com.example.home.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.example.model.SortTask
import com.example.model.Task
import com.example.model.Theme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalTime

@Stable
sealed interface HomeUiState {
    @Immutable
    data object Loading : HomeUiState

    @Immutable
    data class Success(
        val completedTasks: ImmutableList<Task> = persistentListOf(),
        val incompleteTasks: ImmutableList<Task> = persistentListOf(),
        val sleepTime: LocalTime,
        val sortTask: SortTask,
        val theme: Theme,
        val buildVersion: String,
    ) : HomeUiState
}