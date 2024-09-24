package com.example.design_system.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.commandiron.wheel_picker_compose.WheelTimePicker
import com.commandiron.wheel_picker_compose.core.TimeFormat
import com.example.design_system.theme.TodoTheme
import java.time.LocalTime

@Composable
fun ScrollTimePicker(
    initTime: LocalTime,
    onSelect: (LocalTime) -> Unit,
) {
    AnimatedVisibility(
        visible = true,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically(tween(0))
    ) {
        WheelTimePicker(
            timeFormat = TimeFormat.AM_PM,
            startTime = initTime,
            textColor = MaterialTheme.colorScheme.onSecondaryContainer,
            textStyle = TodoTheme.typography.taskTextStyle,
            onSnappedTime = onSelect
        )
    }
}

@Preview
@Composable
private fun ScrollTimePickerPreview() {
    TodoTheme {
        ScrollTimePicker(
            initTime = LocalTime.now(),
            onSelect = {}
        )
    }
}