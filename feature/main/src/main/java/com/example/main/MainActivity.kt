package com.example.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.design_system.theme.TodoTheme
import com.example.model.Theme
import com.example.model.ThemeType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setContent {
            val theme by viewModel.theme.collectAsStateWithLifecycle(
                initialValue = Theme(ThemeType.SYSTEM),
                lifecycleOwner = this
            )

            val navigator = rememberMainNavigator()

            TodoTheme(theme) {
                MainScreen(navigator)
            }
        }
    }
}