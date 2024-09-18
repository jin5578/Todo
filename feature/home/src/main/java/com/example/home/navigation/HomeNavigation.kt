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
    onShowErrorSnackbar: (throwable: Throwable?) -> Unit,
    onShowMessageSnackBar: (message: String) -> Unit
) {
    composable<Route.Home> {
        HomeRoute(
            onShowErrorSnackbar = onShowErrorSnackbar,
            onShowMessageSnackBar = onShowMessageSnackBar,
        )
    }
}