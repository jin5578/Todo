package com.example.add_task.component

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.add_task.R
import com.example.design_system.theme.TodoTheme
import com.example.design_system.theme.priorityColors
import com.example.model.Priority

@Composable
internal fun AddTaskPriority(
    modifier: Modifier = Modifier,
    initPriority: Priority,
    onSelect: (Priority) -> Unit
) {
    var selectedPriority by remember { mutableStateOf(initPriority) }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        Text(
            text = stringResource(R.string.priority),
            style = TodoTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Priority.entries.forEach { priority ->
                AddTaskPriorityItem(
                    modifier = Modifier.weight(1f),
                    title = priority.type,
                    backgroundColor = priorityColors[priority.ordinal],
                    isSelected = selectedPriority == priority,
                    onClick = {
                        onSelect(priority)
                        selectedPriority = priority
                    }
                )
            }
        }
    }
}

@Composable
private fun AddTaskPriorityItem(
    modifier: Modifier = Modifier,
    title: String,
    backgroundColor: Color,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        var bgColor = MaterialTheme.colorScheme.secondaryContainer
        var textColor = MaterialTheme.colorScheme.onSecondaryContainer

        if (isSelected) {
            bgColor = backgroundColor
            textColor = MaterialTheme.colorScheme.surface
        }

        Box(
            modifier = Modifier.fillMaxWidth()
                .background(
                    color = bgColor,
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                style = TodoTheme.typography.headlineSmall,
                color = textColor,
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 16.dp)
            )
        }

        if (isSelected) {
            val animValue = remember { Animatable(initialValue = 0f) }
            LaunchedEffect(Unit) {
                animValue.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(300)
                )
            }
            Box(
                modifier = Modifier.width(40.dp * animValue.value)
                    .height(4.dp)
                    .background(backgroundColor, RoundedCornerShape(8.dp))
            )
        }
    }
}

@Preview
@Composable
private fun AddTaskPriorityPreview() {
    TodoTheme {
        AddTaskPriority(
            initPriority = Priority.HIGH,
            onSelect = {}
        )
    }
}