package com.example.tasks.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.navigation.Route
import com.example.tasks.TasksRoute

fun NavController.navigateTasks(title: String) {
    navigate(route = Route.Tasks(title))
}

fun NavGraphBuilder.tasksNavGraph(
    popBackStack: () -> Unit,
    onShowErrorSnackbar: (throwable: Throwable?) -> Unit,
    onShowMessageSnackBar: (message: String) -> Unit,
) {
    composable<Route.Tasks> { navBackStackEntry ->
        val title = navBackStackEntry.toRoute<Route.Tasks>().title

        TasksRoute(
            title = title,
            popBackStack = popBackStack,
            onShowErrorSnackbar = onShowErrorSnackbar,
            onShowMessageSnackBar = onShowMessageSnackBar
        )
    }
}