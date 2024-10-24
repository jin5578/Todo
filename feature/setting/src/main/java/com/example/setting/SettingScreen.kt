package com.example.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.design_system.component.Loading
import com.example.design_system.theme.TodoTheme
import com.example.model.Theme
import com.example.model.ThemeType
import com.example.model.TimePicker
import com.example.setting.component.SettingCategory
import com.example.setting.component.SettingThemeBottomSheet
import com.example.setting.component.SettingTimePickerBottomSheet
import com.example.setting.model.BottomSheetType
import com.example.setting.model.CategoryItemUiState
import com.example.setting.model.SettingUiState
import com.example.utils.openUrl
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.collectLatest
import com.example.design_system.R as DesignSystemR

@Composable
internal fun SettingRoute(
    viewModel: SettingViewModel = hiltViewModel(),
    navigateInfo: () -> Unit,
    navigateManageCategories: () -> Unit,
    popBackStack: () -> Unit,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
    onShowMessageSnackBar: (message: String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    LaunchedEffect(true) {
        viewModel.errorFlow.collectLatest { throwable ->
            onShowErrorSnackBar(throwable)
        }
    }

    SettingContent(
        uiState = uiState,
        navigateInfo = navigateInfo,
        navigateManageCategories = navigateManageCategories,
        popBackStack = popBackStack,
        openUrl = { url -> openUrl(context, url) },
        onThemeChanged = viewModel::updateTheme,
        onTimePickerChanged = viewModel::updateTimePicker,
        onShowMessageSnackBar = onShowMessageSnackBar
    )
}

@Composable
private fun SettingContent(
    uiState: SettingUiState,
    navigateInfo: () -> Unit,
    navigateManageCategories: () -> Unit,
    popBackStack: () -> Unit,
    openUrl: (String) -> Unit,
    onThemeChanged: (ThemeType) -> Unit,
    onTimePickerChanged: (TimePicker) -> Unit,
    onShowMessageSnackBar: (message: String) -> Unit,
) {
    when (uiState) {
        is SettingUiState.Loading -> {
            Loading()
        }

        is SettingUiState.Success -> {
            SettingScreen(
                theme = uiState.theme,
                timePicker = uiState.timePicker,
                buildVersion = uiState.buildVersion,
                navigateInfo = navigateInfo,
                navigateManageCategories = navigateManageCategories,
                popBackStack = popBackStack,
                openUrl = openUrl,
                onThemeChanged = onThemeChanged,
                onTimePickerChanged = onTimePickerChanged,
                onShowMessageSnackBar = onShowMessageSnackBar
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingScreen(
    theme: Theme,
    timePicker: TimePicker,
    buildVersion: String,
    navigateInfo: () -> Unit,
    navigateManageCategories: () -> Unit,
    popBackStack: () -> Unit,
    openUrl: (String) -> Unit,
    onThemeChanged: (ThemeType) -> Unit,
    onTimePickerChanged: (TimePicker) -> Unit,
    onShowMessageSnackBar: (message: String) -> Unit,
) {
    val scrollState = rememberScrollState()

    val bottomSheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(BottomSheetType.IDLE) }

    val infoCategory = persistentListOf(
        CategoryItemUiState(
            title = DesignSystemR.string.about,
            icon = DesignSystemR.drawable.svg_information,
            onClick = { openUrl("https://intelligent-party-142.notion.site/TODO-1109ff809974806cb274f0b95d4a71d4?pvs=4") }
        ),
        CategoryItemUiState(
            title = DesignSystemR.string.github,
            icon = DesignSystemR.drawable.svg_github,
            onClick = { openUrl("https://github.com/jin5578") }
        )
    )

    val systemCategory = persistentListOf(
        CategoryItemUiState(
            title = DesignSystemR.string.theme,
            icon = DesignSystemR.drawable.svg_theme,
            onClick = {
                showBottomSheet = BottomSheetType.THEME
            },
        ),
        CategoryItemUiState(
            title = DesignSystemR.string.time_picker,
            icon = DesignSystemR.drawable.svg_clock,
            onClick = {
                showBottomSheet = BottomSheetType.TIME_PICKER
            },
        ),
        CategoryItemUiState(
            title = DesignSystemR.string.category,
            icon = DesignSystemR.drawable.svg_category,
            onClick = { navigateManageCategories() }
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                ),
                title = {
                    Text(
                        text = stringResource(id = DesignSystemR.string.settings),
                        style = TodoTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = popBackStack) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = ImageVector.vectorResource(DesignSystemR.drawable.svg_arrow_left),
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
                        BottomSheetType.THEME -> {
                            SettingThemeBottomSheet(
                                initTheme = theme.type,
                                onSelect = onThemeChanged,
                            )
                        }

                        BottomSheetType.TIME_PICKER -> {
                            SettingTimePickerBottomSheet(
                                initTimePicker = timePicker,
                                onSelect = onTimePickerChanged,
                            )
                        }

                        else -> {}
                    }
                }
            }
        }
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                SettingCategory(
                    title = DesignSystemR.string.info,
                    category = infoCategory,
                )
                SettingCategory(
                    title = DesignSystemR.string.system_setting,
                    category = systemCategory,
                )
            }

            Text(
                modifier = Modifier.padding(bottom = 20.dp),
                text = "Version $buildVersion",
                style = TodoTheme.typography.infoDescTextStyle,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview
@Composable
private fun SettingScreenPreview() {
    TodoTheme {
        SettingScreen(
            theme = Theme(ThemeType.SUN_RISE),
            timePicker = TimePicker.SCROLL_TIME_PICKER,
            buildVersion = "1.0.0",
            navigateInfo = {},
            navigateManageCategories = {},
            popBackStack = {},
            openUrl = {},
            onThemeChanged = {},
            onTimePickerChanged = {},
            onShowMessageSnackBar = {}
        )
    }
}