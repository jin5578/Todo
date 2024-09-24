package com.example.model

import androidx.annotation.StringRes
import java.time.LocalDate

data class DateOption(
    @StringRes val title: Int,
    val date: LocalDate,
    val onClick: (LocalDate) -> Unit,
)