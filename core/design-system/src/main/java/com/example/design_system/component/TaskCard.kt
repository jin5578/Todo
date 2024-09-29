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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.R
import com.example.design_system.theme.TodoTheme
import com.example.design_system.theme.priorityColors
import com.example.model.Category
import com.example.model.Priority
import com.example.model.Task
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun TaskCard(
    task: Task,
    category: Category? = null,
    isAvailableSwipe: Boolean,
    onTaskEdit: (Long) -> Unit,
    onTaskToggleCompletion: (Long, Boolean) -> Unit,
    onTaskDelete: (Long) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.surfaceContainer,
                RoundedCornerShape(8.dp)
            )
            .clickable {
                onTaskEdit(task.id)
            }
            .padding(
                horizontal = 8.dp,
                vertical = 10.dp,
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
                        modifier = Modifier.size(21.dp),
                        painter = painterResource(id = R.drawable.svg_check_circle),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                } else {
                    Box(
                        modifier = Modifier.size(20.dp)
                            .border(
                                width = 2.dp,
                                color = MaterialTheme.colorScheme.primary,
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
                    style = TodoTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                if (task.memo.isNotEmpty()) {
                    Text(
                        modifier = Modifier.fillMaxWidth()
                            .padding(end = 10.dp)
                            .basicMarquee(),
                        text = task.memo,
                        style = TodoTheme.typography.infoTextStyle,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    val priorityType = Priority.entries[task.priority].type
                    ExtraInfo(
                        painter = painterResource(id = R.drawable.svg_flag),
                        title = priorityType,
                        textColor = priorityColors[task.priority],
                        tint = priorityColors[task.priority],
                    )
                    if (category != null) {
                        CatgegoryInfo(
                            color = Color(category.colorValue.toLong()),
                            title = category.title
                        )
                    }
                    val timeFormat = DateTimeFormatter.ofPattern("hh:mm a", Locale.US)
                    ExtraInfo(
                        painter = painterResource(id = R.drawable.svg_clock),
                        title = task.time.format(timeFormat),
                        textColor = MaterialTheme.colorScheme.onSurface,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                    ExtraInfo(
                        painter = painterResource(id = R.drawable.svg_calendar),
                        title = task.date.toString(),
                        textColor = MaterialTheme.colorScheme.onSurface,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            if (!isAvailableSwipe) {
                IconButton(
                    modifier = Modifier.weight(0.1f),
                    onClick = { onTaskDelete(task.id) },
                ) {
                    Icon(
                        modifier = Modifier.size(18.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.svg_delete),
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Composable
private fun CatgegoryInfo(
    color: Color,
    title: String
) {
    Row(
        modifier = Modifier.background(
            color = MaterialTheme.colorScheme.surfaceDim,
            shape = RoundedCornerShape(8.dp)
        )
            .padding(horizontal = 4.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier.size(14.dp)
                .background(
                    color = color,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center,
            content = {}
        )
        Text(
            text = title,
            style = TodoTheme.typography.taskDescTextStyle,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun ExtraInfo(
    painter: Painter,
    title: String,
    textColor: Color,
    tint: Color,
) {
    Row(
        modifier = Modifier.background(
            color = MaterialTheme.colorScheme.surfaceDim,
            shape = RoundedCornerShape(8.dp)
        )
            .padding(horizontal = 4.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.size(14.dp),
            painter = painter,
            contentDescription = null,
            tint = tint
        )
        Text(
            text = title,
            style = TodoTheme.typography.taskDescTextStyle,
            color = textColor
        )
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
            time = LocalTime.now(),
            date = LocalDate.now(),
            memo = "memo",
            priority = 2,
            categoryId = -1L,
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