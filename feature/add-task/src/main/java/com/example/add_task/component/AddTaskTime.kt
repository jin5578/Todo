package com.example.add_task.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.add_task.R
import com.example.design_system.component.ClockTimePicker
import com.example.design_system.component.ScrollTimePicker
import com.example.design_system.theme.TodoTheme
import com.example.model.TimePicker
import java.time.LocalTime

@Composable
internal fun AddTaskTime(
    modifier: Modifier = Modifier,
    timePicker: TimePicker = TimePicker.CLOCK_TIME_PICKER,
    startTime: LocalTime,
    endTime: LocalTime,
    onSelectStartTime: (LocalTime) -> Unit,
    onSelectEndTime: (LocalTime) -> Unit,
    onShowStartTimePickerDialog: () -> Unit,
    onShowEndTimePickerDialog: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        Text(
            text = stringResource(R.string.time),
            style = TodoTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            Column(
                modifier = Modifier.aspectRatio(1.6f)
                    .weight(1f)
                    .background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = RoundedCornerShape(8.dp)
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.start_time),
                    style = TodoTheme.typography.taskTextStyle,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Spacer(modifier = Modifier.height(10.dp))
                if (timePicker == TimePicker.CLOCK_TIME_PICKER) {
                    ClockTimePicker(
                        initTime = startTime,
                        onClick = onShowStartTimePickerDialog
                    )
                } else {
                    ScrollTimePicker(
                        initTime = startTime,
                        onSelect = onSelectStartTime
                    )
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier.aspectRatio(1.6f)
                    .weight(1f)
                    .background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = RoundedCornerShape(8.dp)
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.end_time),
                    style = TodoTheme.typography.taskTextStyle,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Spacer(modifier = Modifier.height(10.dp))
                if (timePicker == TimePicker.CLOCK_TIME_PICKER) {
                    ClockTimePicker(
                        initTime = endTime,
                        onClick = onShowEndTimePickerDialog
                    )
                } else {
                    ScrollTimePicker(
                        initTime = endTime,
                        onSelect = onSelectEndTime
                    )
                }
            }
        }
    }
}

@Preview()
@Composable
private fun AddTaskTimePreview() {
    TodoTheme {
        AddTaskTime(
            timePicker = TimePicker.CLOCK_TIME_PICKER,
            startTime = LocalTime.now(),
            endTime = LocalTime.now().plusMinutes(30),
            onSelectStartTime = {},
            onSelectEndTime = {},
            onShowStartTimePickerDialog = {},
            onShowEndTimePickerDialog = {}
        )
    }
}