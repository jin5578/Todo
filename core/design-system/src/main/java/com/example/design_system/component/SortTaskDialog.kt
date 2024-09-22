package com.example.design_system.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.design_system.R
import com.example.design_system.theme.TodoTheme
import com.example.model.SortTask

@Composable
fun SortTaskDialog(
    initSortTask: SortTask,
    onCloseClick: () -> Unit,
    onSelectClick: (SortTask) -> Unit,
) {
    var selectedSortTask by remember { mutableStateOf(initSortTask) }

    Dialog(
        onDismissRequest = onCloseClick
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(1f),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
            )
        ) {
            Text(
                modifier = Modifier.padding(
                    start = 16.dp,
                    top = 24.dp,
                    end = 16.dp,
                    bottom = 16.dp,
                ),
                text = stringResource(R.string.sort_tasks_by),
                style = TodoTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )

            SortTask.entries.forEach {
                CustomRadioButton(
                    label = it.type,
                    isSelected = selectedSortTask == it,
                    onClick = { selectedSortTask = it }
                )
            }

            Text(
                modifier = Modifier.padding(
                    bottom = 24.dp,
                    end = 32.dp
                )
                    .clickable { onSelectClick(selectedSortTask) }
                    .align(Alignment.End),
                text = stringResource(R.string.select),
                style = TodoTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

@Composable
private fun CustomRadioButton(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(bottom = 8.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(
            colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.onSecondaryContainer),
            selected = isSelected,
            onClick = { onClick() })
        Text(
            text = label,
            style = TodoTheme.typography.taskTextStyle,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}

@Preview
@Composable
private fun SortTaskDialogPreview() {
    TodoTheme {
        SortTaskDialog(
            initSortTask = SortTask.BY_CREATE_TIME_ASCENDING,
            onCloseClick = { },
            onSelectClick = { }
        )
    }
}