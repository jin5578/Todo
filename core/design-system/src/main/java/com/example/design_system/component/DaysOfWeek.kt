package com.example.design_system.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.design_system.theme.TodoTheme
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun DaysOfWeek(
    daysOfWeek: List<DayOfWeek>
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.US),
                textAlign = TextAlign.Center,
                style = TodoTheme.typography.infoDescTextStyle,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

@Preview
@Composable
private fun DaysOfWeekPreview() {
    TodoTheme {
        DaysOfWeek(DayOfWeek.entries)
    }
}