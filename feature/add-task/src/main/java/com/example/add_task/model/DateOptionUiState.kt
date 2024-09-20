package com.example.add_task.model

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import java.time.LocalDate

@Immutable
data class DateOptionUiState(
    @StringRes val title: Int,
    val date: LocalDate,
    val onClick: (LocalDate) -> Unit,
)
