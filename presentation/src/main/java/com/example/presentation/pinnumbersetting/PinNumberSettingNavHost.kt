package com.example.presentation.pinnumbersetting

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.presentation.main.MainActivity
import com.example.presentation.pinnumbersetting.setting.SettingScreen
import com.example.presentation.pinnumbersetting.verification.VerificationScreen

@Composable
fun PinNumberSettingNavHost(
    startDestination: PinNumberSettingRoute
) {
    val context = LocalContext.current
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination.routeName
    ) {
        composable(route = PinNumberSettingRoute.VERIFICATION.routeName) {
            VerificationScreen(
                onNavigateToSetting = {
                    navController.navigate(
                        route = PinNumberSettingRoute.SETTING.routeName,
                        navOptions = navOptions {
                            popUpTo(PinNumberSettingRoute.SETTING.routeName)
                        }
                    )
                }
            )
        }

        composable(route = PinNumberSettingRoute.SETTING.routeName) {
            SettingScreen() {
                MainActivity.startActivity(context)
            }
        }
    }
}