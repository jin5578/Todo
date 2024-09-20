package com.example.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.example.add_task.navigation.addTaskNavGraph
import com.example.home.navigation.homeNavGraph
import com.example.setting.navigation.settingNavGraph

@Composable
internal fun MainNavHost(
    modifier: Modifier = Modifier,
    navigator: MainNavigator,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
    onShowMessageSnackBar: (message: String) -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        NavHost(
            navController = navigator.navController,
            startDestination = navigator.startDestination,
        ) {
            homeNavGraph(
                navigateCalendar = {},
                navigateSetting = {
                    navigator.navigateSetting()
                },
                navigateAddTask = navigator::navigateAddTask,
                navigateCompletedTask = {},
                navigateIncompleteTask = {},
                navigateThisWeekTask = {},
                navigateAllTask = {},
                navigateEditTask = {},
                onShowErrorSnackbar = onShowErrorSnackBar,
                onShowMessageSnackBar = onShowMessageSnackBar,
            )
            settingNavGraph(
                navigateInfo = {},
                popBackStack = navigator::popBackStackIfNotHome,
                onShowErrorSnackbar = onShowErrorSnackBar,
                onShowMessageSnackBar = onShowMessageSnackBar,
            )
            addTaskNavGraph(
                popBackStack = navigator::popBackStackIfNotHome,
                onShowErrorSnackbar = onShowErrorSnackBar,
                onShowMessageSnackBar = onShowMessageSnackBar,
            )
        }
    }
}