package com.example.design_system.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import java.time.LocalDate

@Composable
fun MonthDay(
    day: CalendarDay,
    isSelected: Boolean,
    onClick: (LocalDate) -> Unit
) {
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.tertiary else Color.Transparent
    val borderWidth = if (day.date == LocalDate.now()) 1.dp else (-1).dp

    val textColor = if (isSelected)
        MaterialTheme.colorScheme.onSurface
    else {
        if (day.position == DayPosition.MonthDate)
            MaterialTheme.colorScheme.onPrimary
        else
            MaterialTheme.colorScheme.onTertiary
    }
}