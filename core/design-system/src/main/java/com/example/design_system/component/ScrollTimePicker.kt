package com.example.design_system.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.commandiron.wheel_picker_compose.WheelTimePicker
import com.commandiron.wheel_picker_compose.core.TimeFormat
import java.time.LocalTime

@Composable
fun ScrollTimePicker(
    defaultTime: LocalTime,
    onSelect: (LocalTime) -> Unit,
) {
    AnimatedVisibility(
        visible = true,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically(tween(0))
    ) {
        WheelTimePicker(
            timeFormat = TimeFormat.AM_PM,
            startTime = defaultTime,
            textColor = MaterialTheme.colorScheme.onPrimary,
            onSnappedTime = onSelect
        )
    }
}