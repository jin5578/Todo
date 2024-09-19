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
    navigateToCalendarScreen: () -> Unit,
    navigateToSettingScreen: () -> Unit,
    navigateToAddTaskScreen: () -> Unit,
    navigateToCompletedTaskScreen: () -> Unit,
    navigateToIncompleteTaskScreen: () -> Unit,
    navigateToThisWeekTaskScreen: () -> Unit,
    navigateToAllTaskScreen: () -> Unit,
    navigateToEditTaskScreen: (Long) -> Unit,
    onShowErrorSnackbar: (throwable: Throwable?) -> Unit,
    onShowMessageSnackBar: (message: String) -> Unit
) {
    composable<Route.Home> {
        HomeRoute(
            navigateToCalendarScreen = navigateToCalendarScreen,
            navigateToSettingScreen = navigateToSettingScreen,
            navigateToAddTaskScreen = navigateToAddTaskScreen,
            navigateToCompletedTaskScreen = navigateToCompletedTaskScreen,
            navigateToIncompleteTaskScreen = navigateToIncompleteTaskScreen,
            navigateToThisWeekTaskScreen = navigateToThisWeekTaskScreen,
            navigateToAllTaskScreen = navigateToAllTaskScreen,
            navigateToEditTaskScreen = navigateToEditTaskScreen,
            onShowErrorSnackbar = onShowErrorSnackbar,
            onShowMessageSnackBar = onShowMessageSnackBar,
        )
    }
}