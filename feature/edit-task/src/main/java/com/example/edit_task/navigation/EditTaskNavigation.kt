package com.example.edit_task.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.edit_task.EditTaskRoute
import com.example.navigation.Route

fun NavController.navigateEditTask(taskId: Long) {
    navigate(route = Route.EditTask(taskId))
}

fun NavGraphBuilder.editTaskNavGraph(
    popBackStack: () -> Unit,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
    onShowMessageSnackBar: (message: String) -> Unit,
) {
    composable<Route.EditTask> { navBackStackEntry ->
        val taskId = navBackStackEntry.toRoute<Route.EditTask>().taskId

        EditTaskRoute(
            taskId = taskId,
            popBackStack = popBackStack,
            onShowErrorSnackBar = onShowErrorSnackBar,
            onShowMessageSnackBar = onShowMessageSnackBar
        )
    }
}