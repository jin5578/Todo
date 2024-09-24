package com.example.design_system.component

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
    initDay: LocalDate = LocalDate.now(),
    onClose: (LocalDate) -> Unit,
    onShowMessageSnackBar: (message: String) -> Unit,
) {
    val scope = rememberCoroutineScope()

    var selectedDay by remember { mutableStateOf(initDay) }
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
        onDismissRequest = { onClose(initDay) }
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.calendar),
                        style = TodoTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                    )
                    Text(
                        text = selectedDay.format(dateFormat),
                        style = TodoTheme.typography.infoDescTextStyle,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
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
                            tint = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Text(
                            text = title,
                            style = TodoTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
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
                            tint = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                    HorizontalCalendar(
                        state = calendarState,
                        dayContent = { day ->
                            MonthDay(
                                day = day,
                                isSelected = selectedDay == day.date,
                                onClick = { selectedDay = it },
                                onShowMessageSnackBar = onShowMessageSnackBar,
                            )
                        },
                        monthHeader = { month ->
                            val daysOfWeek = month.weekDays.first().map {
                                it.date.dayOfWeek
                            }
                            DaysOfWeek(daysOfWeek)
                        }
                    )
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                            .align(Alignment.End)
                            .clickable {
                                onClose(selectedDay)
                            },
                        text = stringResource(R.string.done),
                        style = TodoTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
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
            initDay = LocalDate.now(),
            onClose = {},
            onShowMessageSnackBar = {},
        )
    }
}