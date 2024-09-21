package com.example.design_system.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.design_system.R
import com.example.design_system.theme.TodoTheme
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun DatePickerDialog(
    defaultDay: LocalDate = LocalDate.now(),
    onClose: (LocalDate) -> Unit
) {
    val scope = rememberCoroutineScope()

    var selectedDay by remember { mutableStateOf(defaultDay) }
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() }

    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth }
    val endMonth = remember { currentMonth.plusMonths(12) }

    val calendarState = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = firstDayOfWeek
    )

    val dateFormat = DateTimeFormatter.ofPattern("d MMMM, yyyy", Locale.US)

    Dialog(
        onDismissRequest = { onClose(defaultDay) }
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.calendar),
                        style = TodoTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Text(
                        text = selectedDay.format(dateFormat),
                        style = TodoTheme.typography.infoDescTextStyle,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
                Column(
                    modifier = Modifier.padding(
                        horizontal = 8.dp,
                        vertical = 16.dp
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    val title = calendarState.lastVisibleMonth.yearMonth.month.getDisplayName(
                        TextStyle.FULL,
                        Locale.US
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Icon(
                            modifier = Modifier.size(32.dp)
                                .padding(8.dp)
                                .clip(CircleShape)
                                .clickable {
                                    scope.launch {
                                        calendarState.animateScrollToMonth(
                                            calendarState.firstVisibleMonth.yearMonth.minusMonths(
                                                1
                                            )
                                        )
                                    }
                                },
                            imageVector = ImageVector.vectorResource(R.drawable.svg_calendar_arrow_left),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = title,
                            style = TodoTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                        Icon(
                            modifier = Modifier.size(32.dp)
                                .padding(8.dp)
                                .clip(CircleShape)
                                .clickable {
                                    scope.launch {
                                        calendarState.animateScrollToMonth(
                                            calendarState.firstVisibleMonth.yearMonth.plusMonths(
                                                1
                                            )
                                        )
                                    }
                                },
                            imageVector = ImageVector.vectorResource(R.drawable.svg_calendar_arrow_right),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    /*HorizontalCalendar(
                        state = calendarState,
                        dayContent = { day ->

                        },
                    )*/
                }
            }
        }
    }
}

@Preview
@Composable
private fun DatePickerDialogPreview() {
    TodoTheme {
        DatePickerDialog(
            defaultDay = LocalDate.now(),
            onClose = {}
        )
    }
}