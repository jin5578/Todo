package com.example.widget.model

import androidx.compose.runtime.Immutable
import com.example.model.Task

@Immutable
data class WidgetTaskCardUiState(
    val task: Task
) {

}