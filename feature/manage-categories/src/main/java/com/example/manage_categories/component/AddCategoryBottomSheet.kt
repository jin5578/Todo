package com.example.manage_categories.component

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.design_system.theme.TodoTheme
import com.example.model.CategoryColor
import kotlinx.coroutines.job
import com.example.design_system.R as DesignSystemR

@Composable
internal fun AddCategoryBottomSheet(
    onCancelClick: () -> Unit,
    onCreateClick: (title: String, categoryColor: CategoryColor) -> Unit,
) {
    val scrollState = rememberScrollState()
    val focusRequester = FocusRequester()

    var categoryTitle by remember { mutableStateOf("") }
    var selectedCategoryColor by remember { mutableStateOf(CategoryColor.RED) }

    LaunchedEffect(
        key1 = true,
        block = {
            coroutineContext.job.invokeOnCompletion {
                focusRequester.requestFocus()
            }
        }
    )

    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        Text(
            text = stringResource(DesignSystemR.string.new_category),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
        )
        TextField(
            modifier = Modifier.fillMaxWidth()
                .focusRequester(focusRequester)
                .clip(shape = RoundedCornerShape(8.dp)),
            value = categoryTitle,
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surfaceDim,
                focusedIndicatorColor = Color.Transparent,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceDim,
                unfocusedIndicatorColor = Color.Transparent,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceDim,
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                cursorColor = MaterialTheme.colorScheme.onSurface,
            ),
            textStyle = TodoTheme.typography.taskTextStyle,
            onValueChange = { categoryTitle = it },
            placeholder = {
                Text(
                    text = stringResource(id = DesignSystemR.string.please_enter_the_category_you_want_to_add),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = TodoTheme.typography.taskTextStyle
                )
            },
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Done
            )
        )
        Row(
            modifier = Modifier.horizontalScroll(scrollState),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            CategoryColor.entries.forEachIndexed { _, categoryColor ->
                CategoryColorItem(
                    categoryColor = categoryColor,
                    isSelected = selectedCategoryColor == categoryColor,
                    onSelect = { selectedCategoryColor = it }
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Box(
                modifier = Modifier.weight(1f)
                    .clickable { onCancelClick() }
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onSurface,
                        shape = RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.padding(
                        horizontal = 24.dp,
                        vertical = 16.dp
                    ),
                    text = stringResource(DesignSystemR.string.cancel),
                    style = TodoTheme.typography.infoTextStyle.copy(fontSize = 16.sp),
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
            Box(
                modifier = Modifier.weight(1f)
                    .clickable {
                        if (categoryTitle.isNotEmpty()) {
                            onCreateClick(categoryTitle, selectedCategoryColor)
                        }
                    }
                    .background(
                        color = MaterialTheme.colorScheme.secondary,
                        shape = RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.padding(
                        horizontal = 24.dp,
                        vertical = 16.dp
                    ),
                    text = stringResource(DesignSystemR.string.create),
                    style = TodoTheme.typography.infoTextStyle.copy(fontSize = 16.sp),
                    color = MaterialTheme.colorScheme.onSecondary,
                )
            }
        }
    }
}

@Preview
@Composable
private fun AddCategoryBottomSheetPreview() {
    TodoTheme {
        AddCategoryBottomSheet(
            onCancelClick = {},
            onCreateClick = { _, _ -> }
        )
    }
}