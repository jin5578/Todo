package com.example.manage_categories.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.R
import com.example.design_system.theme.Red
import com.example.design_system.theme.TodoTheme

@Composable
internal fun CategoryColorItem(
    colorName: String,
    backgroundColor: Color,
    isSelected: Boolean,
    onSelect: (String) -> Unit,
) {
    Box(
        modifier = Modifier.size(40.dp)
            .background(
                color = backgroundColor,
                shape = CircleShape
            )
            .clickable { onSelect(colorName) },
        contentAlignment = Alignment.Center,
        content = {
            if (isSelected) {
                val tint =
                    if (colorName == "Blue" || colorName == "Indigo" || colorName == "Violet") {
                        Color.White
                    } else {
                        Color.Black
                    }
                Icon(
                    modifier = Modifier.size(14.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.svg_check),
                    contentDescription = null,
                    tint = tint,
                )
            }
        }
    )
}

@Preview
@Composable
private fun CategoryColorItemPreview() {
    TodoTheme {
        CategoryColorItem(
            colorName = "Red",
            backgroundColor = Red,
            isSelected = true,
            onSelect = {}
        )
    }
}