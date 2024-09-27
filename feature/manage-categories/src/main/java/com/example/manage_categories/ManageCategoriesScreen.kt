package com.example.manage_categories

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import com.example.manage_categories.component.CategoryCard
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
    onShowMessageSnackBar: (message: String) -> Unit,
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
        onShowMessageSnackBar = onShowMessageSnackBar
    )
}

@Composable
private fun ManageCategoriesContent(
    uiState: ManageCategoriesUiState,
    popBackStack: () -> Unit,
    onShowMessageSnackBar: (message: String) -> Unit,
) {
    when (uiState) {
        is ManageCategoriesUiState.Loading -> {
            Loading()
        }

        is ManageCategoriesUiState.Success -> {
            ManageCategoriesScreen(
                categories = uiState.categories,
                popBackStack = popBackStack,
                onShowMessageSnackBar = onShowMessageSnackBar
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ManageCategoriesScreen(
    categories: ImmutableList<Category>,
    popBackStack: () -> Unit,
    onShowMessageSnackBar: (message: String) -> Unit,
) {
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
            )
        }
    ) { paddingValues ->
        if (categories.isEmpty()) {
            EmptyContent(
                modifier = Modifier.fillMaxSize(),
                title = stringResource(R.string.no_categories)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
                    .padding(paddingValues)
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 10.dp,
                    ),
            ) {
                itemsIndexed(
                    items = categories,
                    key = { _, category ->
                        category.id
                    }
                ) { _, category ->
                    CategoryCard(
                        backgroundColor = category.backgroundColor,
                        onClick = {},
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
            onShowMessageSnackBar = {}
        )
    }
}