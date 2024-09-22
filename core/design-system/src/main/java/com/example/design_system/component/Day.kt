package com.example.design_system.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.TodoTheme
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import java.time.LocalDate

@Composable
fun MonthDay(
    day: CalendarDay,
    isSelected: Boolean,
    onClick: (LocalDate) -> Unit,
    onShowMessageSnackBar: (message: String) -> Unit,
) {
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.secondary else Color.Transparent
    val borderWidth = if (day.date == LocalDate.now()) 1.dp else (-1).dp

    val textColor = if (isSelected)
        MaterialTheme.colorScheme.onSecondary
    else {
        if (day.position == DayPosition.MonthDate)
            MaterialTheme.colorScheme.onSecondaryContainer
        else
            MaterialTheme.colorScheme.outline
    }

    Box(
        modifier = Modifier.aspectRatio(1f)
            .padding(6.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .border(
                width = borderWidth,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable {
                if (day.date < LocalDate.now()) {
                    onShowMessageSnackBar("Past dates cannot be selected.")
                } else {
                    onClick(day.date)
                }
            },
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = day.date.dayOfMonth.toString(),
                style = TodoTheme.typography.taskDescTextStyle,
                color = textColor
            )
        }
    }
}

@Preview
@Composable
private fun MonthDayPreview() {
    TodoTheme {
        MonthDay(
            day = CalendarDay(LocalDate.now(), DayPosition.MonthDate),
            isSelected = false,
            onClick = {},
            onShowMessageSnackBar = {}
        )
        Spacer(modifier = Modifier.height(10.dp))
        MonthDay(
            day = CalendarDay(LocalDate.now(), DayPosition.MonthDate),
            isSelected = false,
            onClick = {},
            onShowMessageSnackBar = {}
        )
    }
}