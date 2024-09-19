package com.example.setting.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Stable
sealed interface SettingUiEffect {
    @Immutable
    data object Idle : SettingUiEffect

    @Immutable
    data object NavigateInfo : SettingUiEffect

    @Immutable
    data class OpenUrl(val url: String) : SettingUiEffect

    @Immutable
    data class ShowBottomSheet(val type: BottomSheetType) : SettingUiEffect
}