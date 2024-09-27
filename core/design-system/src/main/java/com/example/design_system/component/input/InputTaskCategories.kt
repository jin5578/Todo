package com.example.design_system.component.input

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.design_system.R
import com.example.design_system.theme.TodoTheme
import com.example.model.Category
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun InputTaskCategories(
    modifier: Modifier = Modifier,
    categories: ImmutableList<Category>,
    onSelectClick: (Category) -> Unit,
    onDeleteClick: (Category) -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        Text(
            text = stringResource(R.string.category),
            style = TodoTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Row(
            modifier = Modifier.horizontalScroll(scrollState)
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            categories.forEachIndexed { index, category ->
                CategoryItem(
                    title = category.title,
                    backgroundColor = Color.White,
                    textColor = Color.Black,
                    iconColor = Color.Black,
                    onSelectClick = { onSelectClick(categories[index]) },
                    onDeleteClick = { onDeleteClick(categories[index]) }
                )
            }
        }
    }
}

@Composable
private fun CategoryItem(
    modifier: Modifier = Modifier,
    title: String,
    backgroundColor: Color,
    textColor: Color,
    iconColor: Color,
    onSelectClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .clickable { onSelectClick() }
                .background(backgroundColor)
                .padding(horizontal = 10.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            IconButton(
                modifier = Modifier.size(18.dp),
                onClick = { onDeleteClick() },
            ) {
                Icon(
                    modifier = Modifier.size(18.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.svg_cancel),
                    tint = iconColor,
                    contentDescription = null
                )
            }
            Text(
                text = title,
                style = TodoTheme.typography.infoTextStyle.copy(fontSize = 16.sp),
                color = textColor,
            )
        }
    }
}

@Preview
@Composable
private fun InputTaskCategoriesPreview() {
    TodoTheme {
        val categories = persistentListOf(
            Category(
                title = "solum",
                backgroundColor = MaterialTheme.colorScheme.primaryContainer.hashCode()
            )
        )
        InputTaskCategories(
            categories = categories,
            onSelectClick = {},
            onDeleteClick = {},
        )
    }
}