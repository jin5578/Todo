package com.example.setting.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.navigation.Route
import com.example.setting.SettingRoute

fun NavController.navigateSetting() {
    navigate(route = Route.Setting)
}

fun NavGraphBuilder.settingNavGraph(
    navigateInfo: () -> Unit,
    popBackStack: () -> Unit,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
    onShowMessageSnackBar: (message: String) -> Unit,
) {
    composable<Route.Setting> {
        SettingRoute(
            navigateInfo = navigateInfo,
            popBackStack = popBackStack,
            onShowErrorSnackBar = onShowErrorSnackBar,
            onShowMessageSnackBar = onShowMessageSnackBar,
        )
    }
}