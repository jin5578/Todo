package com.example.design_system.component.input

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.R
import com.example.design_system.theme.Red
import com.example.design_system.theme.TodoTheme
import com.example.model.Category
import com.example.model.CategoryColor
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun InputTaskCategories(
    modifier: Modifier = Modifier,
    categories: ImmutableList<Category>,
    categoryId: Long,
    onSelectClick: (Long) -> Unit,
) {
    var selectedCategory by remember { mutableLongStateOf(categoryId) }

    val scrollState = rememberScrollState()

    LaunchedEffect(categoryId) {
        selectedCategory = categoryId
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        Text(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 20.dp),
            text = stringResource(R.string.category),
            style = TodoTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Row(
            modifier = Modifier.horizontalScroll(scrollState)
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            categories.forEachIndexed { _, category ->
                val categoryColor =
                    CategoryColor.entries.filter { it.colorValue == category.colorValue.toLong() }
                        .getOrNull(0) ?: CategoryColor.RED
                CategoryItem(
                    title = category.title,
                    isSelected = category.id == selectedCategory,
                    categoryColor = categoryColor,
                    onSelectClick = {
                        onSelectClick(category.id)
                        selectedCategory = category.id
                    },
                )
            }
        }
    }
}

@Composable
private fun CategoryItem(
    modifier: Modifier = Modifier,
    title: String,
    isSelected: Boolean,
    categoryColor: CategoryColor,
    onSelectClick: () -> Unit,
) {
    val bgColor = if (isSelected)
        MaterialTheme.colorScheme.secondaryContainer
    else
        MaterialTheme.colorScheme.surface

    Row(
        modifier = modifier.fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                shape = RoundedCornerShape(8.dp),
            )
            .background(bgColor)
            .clickable { onSelectClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier.size(10.dp)
                .background(
                    color = Color(categoryColor.colorValue),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center,
            content = {}
        )
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            style = if (isSelected)
                TodoTheme.typography.infoDescTextStyle.copy(fontWeight = FontWeight.Bold)
            else
                TodoTheme.typography.infoDescTextStyle,
        )
    }
}

@Preview
@Composable
private fun InputTaskCategoriesPreview() {
    TodoTheme {
        val categories = persistentListOf(
            Category(
                title = "solum",
                colorValue = Red.value
            )
        )
        InputTaskCategories(
            categories = categories,
            categoryId = -1L,
            onSelectClick = {},
        )
    }
}