package com.example.design_system.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.R
import com.example.design_system.theme.TodoTheme
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun ClockTimePicker(
    initTime: LocalTime,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier.clip(RoundedCornerShape(16.dp))
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onClick() }
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            modifier = Modifier.size(21.dp),
            imageVector = ImageVector.vectorResource(R.drawable.svg_clock),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSecondaryContainer,
        )

        val timeFormat = DateTimeFormatter.ofPattern("hh : mm a", Locale.US)

        Text(
            text = initTime.format(timeFormat),
            style = TodoTheme.typography.taskTextStyle,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}

@Preview
@Composable
private fun ClockTimePickerPreview() {
    TodoTheme {
        ClockTimePicker(
            initTime = LocalTime.now(),
            onClick = {}
        )
    }
}