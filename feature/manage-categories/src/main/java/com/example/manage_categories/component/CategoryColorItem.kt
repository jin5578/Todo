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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.R
import com.example.design_system.theme.TodoTheme
import com.example.model.CategoryColor

@Composable
internal fun CategoryColorItem(
    categoryColor: CategoryColor,
    isSelected: Boolean,
    onSelect: (CategoryColor) -> Unit,
) {
    Box(
        modifier = Modifier.size(40.dp)
            .clip(shape = CircleShape)
            .background(color = Color(categoryColor.colorValue))
            .clickable { onSelect(categoryColor) },
        contentAlignment = Alignment.Center,
        content = {
            if (isSelected) {
                val colorName = categoryColor.colorName
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
            categoryColor = CategoryColor.RED,
            isSelected = true,
            onSelect = {}
        )
    }
}