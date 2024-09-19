package com.example.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.home.HomeRoute
import com.example.navigation.Route

fun NavController.navigateHome(navOptions: NavOptions) {
    navigate(route = Route.Home, navOptions = navOptions)
}

fun NavGraphBuilder.homeNavGraph(
    navigateCalendar: () -> Unit,
    navigateSetting: () -> Unit,
    navigateAddTask: () -> Unit,
    navigateCompletedTask: () -> Unit,
    navigateIncompleteTask: () -> Unit,
    navigateThisWeekTask: () -> Unit,
    navigateAllTask: () -> Unit,
    navigateEditTask: (Long) -> Unit,
    onShowErrorSnackbar: (throwable: Throwable?) -> Unit,
    onShowMessageSnackBar: (message: String) -> Unit
) {
    composable<Route.Home> {
        HomeRoute(
            navigateCalendar = navigateCalendar,
            navigateSetting = navigateSetting,
            navigateAddTask = navigateAddTask,
            navigateCompletedTask = navigateCompletedTask,
            navigateIncompleteTask = navigateIncompleteTask,
            navigateThisWeekTask = navigateThisWeekTask,
            navigateAllTask = navigateAllTask,
            navigateEditTask = navigateEditTask,
            onShowErrorSnackbar = onShowErrorSnackbar,
            onShowMessageSnackBar = onShowMessageSnackBar,
        )
    }
}