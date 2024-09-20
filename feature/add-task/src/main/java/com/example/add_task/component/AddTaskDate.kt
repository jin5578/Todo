package com.example.add_task.component

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.add_task.R
import com.example.add_task.model.DateOptionUiState
import com.example.design_system.theme.TodoTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
internal fun AddTaskDate(
    modifier: Modifier = Modifier,
    date: LocalDate,
    onDateChange: (LocalDate) -> Unit,
    onShowDatePickerDialog: () -> Unit,
) {
    val options = persistentListOf(
        DateOptionUiState(
            title = R.string.today,
            date = LocalDate.now(),
            onClick = { onDateChange(it) }
        ),
        DateOptionUiState(
            title = R.string.tomorrow,
            date = LocalDate.now().plusDays(1),
            onClick = { onDateChange(it) }
        ),
        DateOptionUiState(
            title = R.string.next_week,
            date = LocalDate.now().plusWeeks(1),
            onClick = { onDateChange(it) }
        )
    )

    var selectedDate by remember { mutableStateOf(date) }

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
                text = stringResource(R.string.date),
                style = TodoTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Row(
                modifier = Modifier.clickable { onShowDatePickerDialog() },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val dateFormat = DateTimeFormatter.ofPattern("d MMMM", Locale.US)
                Icon(
                    modifier = Modifier.size(18.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.svg_calendar),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = date.format(dateFormat),
                    style = TodoTheme.typography.infoDescTextStyle
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onSurface,
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

                AddTaskDateItem(
                    modifier = Modifier.weight(1f),
                    title = item.title,
                    date = item.date,
                    isSelected = selectedDate == item.date,
                    shape = shape,
                    onDateChange = {
                        onDateChange(it)
                        selectedDate = item.date
                    },
                )
            }
        }
    }
}

@Composable
private fun AddTaskDateItem(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    date: LocalDate,
    isSelected: Boolean,
    shape: Shape,
    onDateChange: (LocalDate) -> Unit
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
            .clickable { onDateChange(date) }
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(title),
            color = MaterialTheme.colorScheme.onSurface,
            style = TodoTheme.typography.infoDescTextStyle,
        )
    }
}

@Preview
@Composable
private fun AddTaskDatePreview() {
    TodoTheme {
        AddTaskDate(
            date = LocalDate.now(),
            onDateChange = {},
            onShowDatePickerDialog = {}
        )
    }
}