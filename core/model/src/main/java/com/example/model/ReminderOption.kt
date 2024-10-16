package com.example.model

import androidx.annotation.StringRes
import java.time.LocalTime

data class ReminderOption(
    @StringRes val title: Int,
    val isRemind: Boolean,
    val onClick: (Boolean) -> Unit,
)