package com.example.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.example.add_task.navigation.addTaskNavGraph
import com.example.calendar.navigation.calendarNavGraph
import com.example.edit_task.navigation.editTaskNavGraph
import com.example.home.navigation.homeNavGraph
import com.example.manage_categories.navigation.manageCategoriesNavGraph
import com.example.setting.navigation.settingNavGraph
import com.example.tasks.navigation.tasksNavGraph

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
                navigateCalendar = navigator::navigateCalendar,
                navigateSetting = navigator::navigateSetting,
                navigateAddTask = navigator::navigateAddTask,
                navigateCompletedTask = navigator::navigateTasks,
                navigateIncompleteTask = navigator::navigateTasks,
                navigateThisWeekTask = navigator::navigateTasks,
                navigateAllTask = navigator::navigateTasks,
                navigateEditTask = navigator::navigateEditTask,
                onShowErrorSnackBar = onShowErrorSnackBar,
                onShowMessageSnackBar = onShowMessageSnackBar,
            )
            settingNavGraph(
                navigateInfo = {},
                navigateManageCategories = navigator::navigateManageCategories,
                popBackStack = navigator::popBackStackIfNotHome,
                onShowErrorSnackBar = onShowErrorSnackBar,
                onShowMessageSnackBar = onShowMessageSnackBar,
            )
            addTaskNavGraph(
                popBackStack = navigator::popBackStackIfNotHome,
                onShowErrorSnackBar = onShowErrorSnackBar,
                onShowMessageSnackBar = onShowMessageSnackBar,
            )
            editTaskNavGraph(
                popBackStack = navigator::popBackStackIfNotHome,
                onShowErrorSnackBar = onShowErrorSnackBar,
                onShowMessageSnackBar = onShowMessageSnackBar,
            )
            tasksNavGraph(
                popBackStack = navigator::popBackStackIfNotHome,
                navigateEditTask = navigator::navigateEditTask,
                onShowErrorSnackBar = onShowErrorSnackBar,
                onShowMessageSnackBar = onShowMessageSnackBar,
            )
            calendarNavGraph(
                navigateAddTask = navigator::navigateAddTask,
                navigateEditTask = navigator::navigateEditTask,
                popBackStack = navigator::popBackStackIfNotHome,
                onShowErrorSnackBar = onShowErrorSnackBar,
                onShowMessageSnackBar = onShowMessageSnackBar,
            )
            manageCategoriesNavGraph(
                popBackStack = navigator::popBackStackIfNotHome,
                onShowErrorSnackBar = onShowErrorSnackBar,
                onShowMessageSnackBar = onShowMessageSnackBar,
            )
        }
    }
}