package com.example.manage_categories.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.manage_categories.ManageCategoriesRoute
import com.example.navigation.Route

fun NavController.navigateManageCategories() {
    navigate(route = Route.ManageCategories)
}

fun NavGraphBuilder.manageCategoriesNavGraph(
    popBackStack: () -> Unit,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
    onShowMessageSnackBar: (message: String) -> Unit,
) {
    composable<Route.ManageCategories> { navBackStackEntry ->
        ManageCategoriesRoute(
            popBackStack = popBackStack,
            onShowErrorSnackBar = onShowErrorSnackBar,
            onShowMessageSnackBar = onShowMessageSnackBar,
        )
    }
}