package com.example.design_system.component

import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.R
import com.example.design_system.theme.TodoTheme
import com.example.design_system.theme.priorityColors
import com.example.model.Task
import com.example.utils.getTaskTotalTime
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun TaskCard(
    task: Task,
    isAvailableSwipe: Boolean,
    onTaskEdit: (Long) -> Unit,
    onTaskToggleCompletion: (Long, Boolean) -> Unit,
    onTaskDelete: (Long) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxWidth()
            .background(
                priorityColors[task.priority],
                RoundedCornerShape(
                    topStart = 8.dp,
                    bottomStart = 8.dp,
                    topEnd = 20.dp,
                    bottomEnd = 20.dp
                )
            )
            .padding(start = 10.dp)
            .clickable {
                onTaskEdit(task.id)
            }
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.primary,
                    RoundedCornerShape(
                        topEnd = 8.dp,
                        bottomEnd = 8.dp
                    )
                )
                .padding(
                    start = 8.dp,
                    top = 10.dp,
                    end = 8.dp,
                    bottom = 10.dp
                )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(
                    modifier = Modifier.size(32.dp)
                        .weight(0.1f),
                    onClick = { onTaskToggleCompletion(task.id, !task.isCompleted) }
                ) {
                    if (task.isCompleted) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(id = R.drawable.svg_check_circle),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primaryContainer
                        )
                    } else {
                        Box(
                            modifier = Modifier.size(20.dp)
                                .border(
                                    width = 2.dp,
                                    color = MaterialTheme.colorScheme.primaryContainer,
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center,
                            content = {}
                        )
                    }
                }
                Column(
                    modifier = Modifier.weight(0.8f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth()
                            .basicMarquee(),
                        text = task.title,
                        style = TodoTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            modifier = Modifier.size(15.dp),
                            painter = painterResource(id = R.drawable.svg_clock),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                        Text(
                            text = getTaskTotalTime(
                                task.startTime,
                                task.endTime
                            ),
                            style = TodoTheme.typography.taskDescTextStyle,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            modifier = Modifier.size(15.dp),
                            painter = painterResource(id = R.drawable.svg_calendar),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                        Text(
                            text = task.date.toString(),
                            style = TodoTheme.typography.taskDescTextStyle,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
                if (!isAvailableSwipe) {
                    IconButton(
                        modifier = Modifier.weight(0.1f),
                        onClick = { onTaskDelete(task.id) },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            tint = MaterialTheme.colorScheme.onPrimary,
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun TaskCardPreview() {
    TodoTheme {
        val task = Task(
            id = 8578,
            uuid = "corrumpit",
            title = "inceptos",
            isCompleted = false,
            startTime = LocalTime.now(),
            endTime = LocalTime.now(),
            date = LocalDate.now(),
            memo = "memo",
            priority = 2
        )
        TaskCard(
            task = task,
            isAvailableSwipe = false,
            onTaskEdit = {},
            onTaskToggleCompletion = { _, _ -> },
            onTaskDelete = {}
        )
    }
}