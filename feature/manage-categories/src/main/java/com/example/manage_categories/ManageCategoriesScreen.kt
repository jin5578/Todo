package com.example.manage_categories

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.design_system.R
import com.example.design_system.component.EmptyContent
import com.example.design_system.component.Loading
import com.example.design_system.theme.TodoTheme
import com.example.manage_categories.component.AddCategoryBottomSheet
import com.example.manage_categories.component.CategoryCard
import com.example.manage_categories.component.EditCategoryBottomSheet
import com.example.manage_categories.model.BottomSheetType
import com.example.manage_categories.model.CategoryColor
import com.example.manage_categories.model.ManageCategoriesUiState
import com.example.model.Category
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.collectLatest
import com.example.design_system.R as DesignSystemR

@Composable
internal fun ManageCategoriesRoute(
    viewModel: ManageCategoriesViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.errorFlow.collectLatest { throwable ->
            onShowErrorSnackBar(throwable)
        }
    }

    ManageCategoriesContent(
        uiState = uiState,
        popBackStack = popBackStack,
        onCategoryAdd = viewModel::insertCategory,
        onCategoryDelete = viewModel::deleteCategory,
        onCategoryUpdate = viewModel::updateCategory,
    )
}

@Composable
private fun ManageCategoriesContent(
    uiState: ManageCategoriesUiState,
    popBackStack: () -> Unit,
    onCategoryAdd: (title: String, colorName: String) -> Unit,
    onCategoryDelete: (Long) -> Unit,
    onCategoryUpdate: (id: Long, title: String, colorName: String) -> Unit,
) {
    when (uiState) {
        is ManageCategoriesUiState.Loading -> {
            Loading()
        }

        is ManageCategoriesUiState.Success -> {
            ManageCategoriesScreen(
                categories = uiState.categories,
                popBackStack = popBackStack,
                onCategoryAdd = onCategoryAdd,
                onCategoryDelete = onCategoryDelete,
                onCategoryUpdate = onCategoryUpdate,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ManageCategoriesScreen(
    categories: ImmutableList<Category>,
    popBackStack: () -> Unit,
    onCategoryAdd: (title: String, colorName: String) -> Unit,
    onCategoryDelete: (Long) -> Unit,
    onCategoryUpdate: (id: Long, title: String, colorName: String) -> Unit,
) {
    val bottomSheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(BottomSheetType.IDLE) }

    var editId by remember { mutableLongStateOf(0L) }
    var editTitle by remember { mutableStateOf("") }
    var editColorName by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                title = {
                    Text(
                        text = stringResource(DesignSystemR.string.category),
                        style = TodoTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = popBackStack) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = ImageVector.vectorResource(R.drawable.svg_arrow_left),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { showBottomSheet = BottomSheetType.ADD_CATEGORY }
                    ) {
                        Icon(
                            modifier = Modifier.size(21.dp),
                            imageVector = ImageVector.vectorResource(DesignSystemR.drawable.svg_add_category),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        if (showBottomSheet != BottomSheetType.IDLE) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = BottomSheetType.IDLE },
                sheetState = bottomSheetState,
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
            ) {
                Box() {
                    when (showBottomSheet) {
                        BottomSheetType.ADD_CATEGORY -> {
                            AddCategoryBottomSheet(
                                onCancelClick = { showBottomSheet = BottomSheetType.IDLE },
                                onCreateClick = { title, colorName ->
                                    onCategoryAdd(title, colorName)
                                    showBottomSheet = BottomSheetType.IDLE
                                }
                            )
                        }

                        BottomSheetType.EDIT_CATEGORY -> {
                            EditCategoryBottomSheet(
                                id = editId,
                                title = editTitle,
                                colorName = editColorName,
                                onCancelClick = { showBottomSheet = BottomSheetType.IDLE },
                                onEditClick = { id, title, colorName ->
                                    onCategoryUpdate(id, title, colorName)
                                    showBottomSheet = BottomSheetType.IDLE
                                }
                            )
                        }

                        else -> {}
                    }
                }
            }
        }

        if (categories.isEmpty()) {
            EmptyContent(
                modifier = Modifier.fillMaxSize(),
                title = stringResource(R.string.no_categories)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
                    .padding(paddingValues)
            ) {
                itemsIndexed(
                    items = categories,
                    key = { _, category ->
                        category.id
                    }
                ) { _, category ->
                    val color = CategoryColor.entries.filter { it.colorName == category.colorName }
                        .map { it.color }.getOrNull(0) ?: CategoryColor.RED.color

                    CategoryCard(
                        id = category.id,
                        title = category.title,
                        colorName = category.colorName,
                        color = color,
                        onEditClick = { id, title, colorName ->
                            editId = id
                            editTitle = title
                            editColorName = colorName
                            showBottomSheet = BottomSheetType.EDIT_CATEGORY
                        },
                        onDeleteClick = onCategoryDelete
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ManageCategoriesPreview() {
    TodoTheme {
        ManageCategoriesScreen(
            categories = persistentListOf(),
            popBackStack = {},
            onCategoryAdd = { _, _ -> },
            onCategoryDelete = {},
            onCategoryUpdate = { _, _, _ -> },
        )
    }
}