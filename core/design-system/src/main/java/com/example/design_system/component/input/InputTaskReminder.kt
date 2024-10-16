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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.R
import com.example.design_system.theme.TodoTheme
import com.example.model.ReminderOption
import kotlinx.collections.immutable.persistentListOf

@Composable
fun InputTaskReminder(
    modifier: Modifier = Modifier,
    isRemind: Boolean,
    onReminderChanged: (Boolean) -> Unit,
) {
    val options = persistentListOf(
        ReminderOption(
            title = R.string.on,
            isRemind = true,
            onClick = { onReminderChanged(true) }
        ),
        ReminderOption(
            title = R.string.off,
            isRemind = false,
            onClick = { onReminderChanged(false) }
        )
    )

    var selectedReminder by remember { mutableStateOf(isRemind) }

    LaunchedEffect(isRemind) {
        selectedReminder = isRemind
    }

    Column(
        modifier = modifier.fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.reminder),
            style = TodoTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
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

                    else -> {
                        RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp)
                    }
                }

                val isSelected = selectedReminder == item.isRemind

                InputTaskReminderItem(
                    modifier = Modifier.weight(1f),
                    title = item.title,
                    isRemind = item.isRemind,
                    isSelected = isSelected,
                    shape = shape,
                    onReminderChanged = {
                        onReminderChanged(it)
                        selectedReminder = item.isRemind
                    }
                )
            }
        }
    }
}

@Composable
private fun InputTaskReminderItem(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    isRemind: Boolean,
    isSelected: Boolean,
    shape: Shape,
    onReminderChanged: (Boolean) -> Unit,
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
            .clickable { onReminderChanged(isRemind) }
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
private fun InputTaskReminderPreview() {
    TodoTheme {
        Column {
            InputTaskReminder(
                isRemind = true,
                onReminderChanged = {}
            )
        }
    }
}