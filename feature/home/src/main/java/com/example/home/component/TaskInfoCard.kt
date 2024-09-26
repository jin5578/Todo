package com.example.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.TodoTheme
import com.example.design_system.R as DesignSystemR

@Composable
internal fun TaskInfoCard(
    modifier: Modifier,
    title: String,
    icon: Int? = null,
    content: String? = null,
    backgroundColor: Color,
    onClick: (String) -> Unit,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        onClick = { onClick(title) }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(
                    horizontal = 8.dp,
                    vertical = 16.dp,
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                style = TodoTheme.typography.infoTextStyle,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            if (icon != null || content != null) {
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    if (icon != null) {
                        Icon(
                            modifier = Modifier.size(14.dp),
                            contentDescription = null,
                            painter = painterResource(id = icon),
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        )
                    }
                    if (content != null) {
                        Text(
                            text = content,
                            style = TodoTheme.typography.infoDescTextStyle,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun TaskInfoCardPreview() {
    TodoTheme {
        TaskInfoCard(
            modifier = Modifier,
            title = "Completed",
            icon = DesignSystemR.drawable.svg_completed,
            content = "2 Tasks",
            backgroundColor = MaterialTheme.colorScheme.primaryContainer,
            onClick = { }
        )
    }
}