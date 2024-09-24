package com.example.setting.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.design_system.component.ClockTimePicker
import com.example.design_system.component.ScrollTimePicker
import com.example.design_system.component.TimePickerDialog
import com.example.design_system.theme.TodoTheme
import com.example.model.TimePicker
import com.example.setting.R
import java.time.LocalTime

@Composable
internal fun SettingTimePicker(
    initTimePicker: TimePicker,
    onSelect: (TimePicker) -> Unit,
) {
    var selectedTimePicker by remember { mutableStateOf(initTimePicker) }

    var isShowTimePickerDialog by remember { mutableStateOf(false) }
    var selectedTime by remember { mutableStateOf(LocalTime.now()) }

    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(bottom = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(30.dp),
    ) {
        if (isShowTimePickerDialog) {
            TimePickerDialog(
                initTime = selectedTime,
                onClose = {
                    selectedTime = it
                    isShowTimePickerDialog = false
                }
            )
        }
        Text(
            text = stringResource(R.string.choose_time_picker_style),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            ScrollTimePicker(
                initTime = LocalTime.now(),
                onSelect = { }
            )
            ClockTimePicker(
                initTime = LocalTime.now(),
                onClick = { isShowTimePickerDialog = true }
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            ToggleButton(
                timePicker = TimePicker.SCROLL_TIME_PICKER,
                isSelected = selectedTimePicker == TimePicker.SCROLL_TIME_PICKER,
                onClick = {
                    selectedTimePicker = TimePicker.SCROLL_TIME_PICKER
                    onSelect(selectedTimePicker)
                }
            )
            ToggleButton(
                timePicker = TimePicker.CLOCK_TIME_PICKER,
                isSelected = selectedTimePicker == TimePicker.CLOCK_TIME_PICKER,
                onClick = {
                    selectedTimePicker = TimePicker.CLOCK_TIME_PICKER
                    onSelect(selectedTimePicker)
                }
            )
        }
    }
}

@Composable
private fun ToggleButton(
    timePicker: TimePicker,
    isSelected: Boolean,
    onClick: (TimePicker) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier.clip(RoundedCornerShape(8.dp))
                .clickable { onClick(timePicker) }
                .background(
                    if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
                text = timePicker.timePickerName,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = 14.sp
                ),
                color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface

            )
        }
        if (isSelected) {
            val animValue = remember {
                Animatable(initialValue = 0f)
            }

            LaunchedEffect(Unit) {
                animValue.animateTo(1f, tween(300))
            }

            Box(
                modifier = Modifier.width(40.dp * animValue.value)
                    .height(4.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(8.dp),
                    )
            )
        }
    }
}

@Preview
@Composable
private fun SettingTimePickerPreview() {
    TodoTheme {
        SettingTimePicker(
            initTimePicker = TimePicker.SCROLL_TIME_PICKER,
            onSelect = {}
        )
    }
}