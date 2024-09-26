package com.example.design_system.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
import java.time.LocalTime

@Composable
fun ScrollTimePickerDialog(
    initTime: LocalTime = LocalTime.now(),
    onClose: (LocalTime) -> Unit,
) {
    var selectedTime by remember { mutableStateOf(LocalTime.now()) }

    Dialog(
        onDismissRequest = { onClose(initTime) }
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ScrollTimePicker(
                    initTime = initTime,
                    onSelect = { selectedTime = it }
                )
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        .align(Alignment.End)
                        .clickable {
                            onClose(selectedTime)
                        },
                    text = stringResource(R.string.done),
                    style = TodoTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}

@Preview
@Composable
private fun ScrollTimePickerDialogPreview() {
    TodoTheme {
        ScrollTimePickerDialog(
            initTime = LocalTime.now(),
            onClose = {}
        )
    }
}