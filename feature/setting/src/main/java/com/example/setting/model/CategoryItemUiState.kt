package com.example.setting.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable

@Immutable
data class CategoryItemUiState(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val onClick: () -> Unit
)