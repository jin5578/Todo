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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.Black500
import com.example.design_system.theme.Blue
import com.example.design_system.theme.TodoTheme
import com.example.home.R

@Composable
internal fun TaskInfoCard(
    modifier: Modifier,
    title: String,
    icon: Int,
    content: String,
    backgroundColor: Color,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        onClick = onClick
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
                color = Black500
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    contentDescription = null,
                    painter = painterResource(id = icon),
                    tint = Black500,
                )
                Text(
                    text = content,
                    style = TodoTheme.typography.infoDescTextStyle,
                    color = Black500
                )
            }
        }
    }
}

@Preview
@Composable
private fun TaskInfoCardPreview() {
    TaskInfoCard(
        modifier = Modifier,
        title = "Completed",
        icon = R.drawable.svg_task_list,
        content = "2 Tasks",
        backgroundColor = Blue,
        onClick = { }
    )
}