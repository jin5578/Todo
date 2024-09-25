package com.example.setting.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.TodoTheme
import com.example.setting.R
import com.example.setting.model.CategoryItemUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun SettingCategory(
    @StringRes title: Int,
    category: ImmutableList<CategoryItemUiState>
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 0.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = stringResource(title),
            style = TodoTheme.typography.infoDescTextStyle,
            color = MaterialTheme.colorScheme.onSurface
        )
        Column {
            category.forEachIndexed { index, item ->
                CategoryItem(
                    title = item.title,
                    icon = item.icon,
                    onClick = item.onClick
                )
                if (index != category.lastIndex) {
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }
}

@Composable
private fun CategoryItem(
    @StringRes title: Int,
    @DrawableRes icon: Int,
    onClick: () -> Unit,
) {
    Row(modifier = Modifier.fillMaxWidth()
        .clickable { onClick() }
        .background(
            color = MaterialTheme.colorScheme.secondaryContainer,
            shape = RoundedCornerShape(8.dp)
        )
        .padding(
            horizontal = 16.dp,
            vertical = 16.dp
        ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Icon(
            modifier = Modifier.size(18.dp),
            painter = painterResource(icon),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSecondaryContainer
        )
        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(title),
            style = TodoTheme.typography.infoTextStyle,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
        Icon(
            modifier = Modifier.size(14.dp),
            imageVector = ImageVector.vectorResource(R.drawable.svg_arrow_right_twin),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}

@Preview
@Composable
private fun SettingCategoryPreview() {
    val infoCategory = persistentListOf(
        CategoryItemUiState(
            title = R.string.about,
            icon = R.drawable.svg_information,
            onClick = {}
        ),
        CategoryItemUiState(
            title = R.string.github,
            icon = R.drawable.svg_github,
            onClick = {}
        )
    )

    TodoTheme {
        SettingCategory(
            title = R.string.info,
            category = infoCategory
        )
    }
}