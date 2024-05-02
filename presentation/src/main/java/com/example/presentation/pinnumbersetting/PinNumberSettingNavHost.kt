package com.example.presentation.pinnumbersetting

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.presentation.pinnumbersetting.setting.SettingScreen
import com.example.presentation.pinnumbersetting.verification.VerificationScreen

@Composable
fun PinNumberSettingNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = PinNumberSettingRoute.VERIFICATION.routeName
    ) {
        composable(route = PinNumberSettingRoute.VERIFICATION.routeName) {
            VerificationScreen()
        }

        composable(route = PinNumberSettingRoute.SETTING.routeName) {
            SettingScreen()
        }
    }
}