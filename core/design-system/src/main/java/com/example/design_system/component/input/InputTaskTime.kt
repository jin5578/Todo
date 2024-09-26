package com.example.design_system.component.input

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.R
import com.example.design_system.theme.TodoTheme
import com.example.model.TimeOption
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun InputTaskTime(
    modifier: Modifier = Modifier,
    time: LocalTime,
    onTimeChange: (LocalTime) -> Unit,
    onShowTimePickerDialog: () -> Unit,
) {
    val options = persistentListOf(
        TimeOption(
            title = R.string.now,
            time = LocalTime.now(),
            onClick = { onTimeChange(it) }
        ),
        TimeOption(
            title = R.string.in_1_hour,
            time = LocalTime.now().plusHours(1),
            onClick = { onTimeChange(it) }
        ),
        TimeOption(
            title = R.string.in_2_hours,
            time = LocalTime.now().plusHours(2),
            onClick = { onTimeChange(it) }
        )
    )

    var selectedTime by remember { mutableStateOf(time) }

    val timeFormat = DateTimeFormatter.ofPattern("hh : mm a", Locale.US)

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.time),
                style = TodoTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Row(
                modifier = Modifier.clickable { onShowTimePickerDialog() },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    modifier = Modifier.size(14.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.svg_clock),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = time.format(timeFormat),
                    style = TodoTheme.typography.infoDescTextStyle,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    shape = RoundedCornerShape(8.dp)
                ),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            options.forEachIndexed { index, item ->
                val shape = when (index) {
                    0 -> {
                        RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)
                    }

                    options.size - 1 -> {
                        RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp)
                    }

                    else -> {
                        RectangleShape
                    }
                }

                val isSelected = selectedTime.format(timeFormat) == item.time.format(timeFormat)

                InputTaskTimeItem(
                    modifier = Modifier.weight(1f),
                    title = item.title,
                    time = item.time,
                    isSelected = isSelected,
                    shape = shape,
                    onTimeChange = {
                        onTimeChange(it)
                        selectedTime = item.time
                    }
                )
            }
        }
    }
}

@Composable
private fun InputTaskTimeItem(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    time: LocalTime,
    isSelected: Boolean,
    shape: Shape,
    onTimeChange: (LocalTime) -> Unit
) {
    val bgColor = if (isSelected)
        MaterialTheme.colorScheme.secondaryContainer
    else
        MaterialTheme.colorScheme.surface

    Box(
        modifier = modifier.fillMaxWidth()
            .background(
                color = bgColor,
                shape = shape
            )
            .clickable { onTimeChange(time) }
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(title),
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            style = if (isSelected)
                TodoTheme.typography.infoDescTextStyle.copy(fontWeight = FontWeight.Bold)
            else
                TodoTheme.typography.infoDescTextStyle,
        )
    }
}

@Preview()
@Composable
private fun InputTaskTimePreview() {
    TodoTheme {
        Column {
            InputTaskTime(
                time = LocalTime.now(),
                onTimeChange = {},
                onShowTimePickerDialog = {},
            )
        }
    }
}