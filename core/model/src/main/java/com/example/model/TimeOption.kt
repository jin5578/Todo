package com.example.model

import androidx.annotation.StringRes
import java.time.LocalTime

data class TimeOption(
    @StringRes val title: Int,
    val time: LocalTime,
    val onClick: (LocalTime) -> Unit,
)