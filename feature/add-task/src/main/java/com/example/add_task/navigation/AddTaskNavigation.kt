package com.example.add_task.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.add_task.AddTaskRoute
import com.example.navigation.Route
import java.time.LocalDate

fun NavController.navigateAddTask(date: LocalDate) {
    navigate(route = Route.AddTask(date.toString()))
}

fun NavGraphBuilder.addTaskNavGraph(
    popBackStack: () -> Unit,
    onShowErrorSnackbar: (throwable: Throwable?) -> Unit,
    onShowMessageSnackBar: (message: String) -> Unit,
) {
    composable<Route.AddTask> {
        AddTaskRoute(
            popBackStack = popBackStack,
            onShowErrorSnackbar = onShowErrorSnackbar,
            onShowMessageSnackBar = onShowMessageSnackBar
        )
    }
}