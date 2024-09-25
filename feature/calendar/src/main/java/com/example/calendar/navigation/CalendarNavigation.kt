package com.example.calendar.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.calendar.CalendarRoute
import com.example.navigation.Route

fun NavController.navigateCalendar() {
    navigate(route = Route.Calendar)
}

fun NavGraphBuilder.calendarNavGraph(
    popBackStack: () -> Unit,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
    onShowMessageSnackBar: (message: String) -> Unit,
) {
    composable<Route.Calendar> {
        CalendarRoute(
            popBackStack = popBackStack,
            onShowErrorSnackBar = onShowErrorSnackBar,
            onShowMessageSnackBar = onShowMessageSnackBar
        )
    }
}