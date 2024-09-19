package com.example.setting.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.example.model.Theme
import com.example.model.TimePicker
import java.time.LocalTime

@Stable
sealed interface SettingUiState {
    @Immutable
    data object Loading : SettingUiState

    @Immutable
    data class Success(
        val theme: Theme,
        val sleepTime: LocalTime,
        val timePicker: TimePicker,
        val buildVersion: String,
    ) : SettingUiState
}