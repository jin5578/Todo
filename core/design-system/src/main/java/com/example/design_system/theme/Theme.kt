package com.example.design_system.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.example.model.Theme
import com.example.model.ThemeType

val LightColorScheme = lightColorScheme(
    primary = LightPrimary,
    primaryContainer = LightPrimary,
    secondary = LightItemBackgroundL1,
    secondaryContainer = LightItemBackgroundL1,
    background = LightBackgroundL2,
    surface = LightBackgroundL1,
    surfaceVariant = LightItemBackgroundL2,
    error = LightError,
    onPrimary = Color.White,
    onPrimaryContainer = Color.White,
    onSecondary = LightOn,
    onSecondaryContainer = LightOn,
    onBackground = LightOn,
    onSurface = LightOn,
    onSurfaceVariant = LightOn,
    onError = Color.White
)

val TwilightColorScheme = lightColorScheme(
    primary = TwilightPrimary,
    primaryContainer = TwilightPrimary,
    secondary = TwilightItemBackgroundL1,
    secondaryContainer = TwilightItemBackgroundL1,
    background = TwilightBackgroundL2,
    surface = TwilightBackgroundL1,
    surfaceVariant = TwilightItemBackgroundL2,
    error = Error,
    onPrimary = TwilightOn,
    onPrimaryContainer = TwilightOn,
    onSecondary = TwilightOn,
    onSecondaryContainer = TwilightOn,
    onBackground = TwilightOn,
    onSurface = TwilightOn,
    onSurfaceVariant = TwilightOn,
    onError = Color.White
)

val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    primaryContainer = DarkPrimary,
    secondary = DarkItemBackgroundL1,
    secondaryContainer = DarkItemBackgroundL1,
    background = DarkBackgroundL2,
    surface = DarkBackgroundL1,
    surfaceVariant = DarkItemBackgroundL2,
    error = Error,
    onPrimary = DarkOn,
    onPrimaryContainer = DarkOn,
    onSecondary = DarkOn,
    onSecondaryContainer = DarkOn,
    onBackground = DarkOn,
    onSurface = DarkOn,
    onSurfaceVariant = DarkOn,
    onError = Color.White
)

val LocalDarkTheme = compositionLocalOf { true }

@Composable
fun TodoTheme(
    theme: Theme = Theme(ThemeType.SYSTEM),
    content: @Composable () -> Unit,
) {
    val colorScheme = when (theme.type) {
        ThemeType.SYSTEM -> {
            if (isSystemInDarkTheme()) {
                DarkColorScheme
            } else {
                LightColorScheme
            }
        }

        ThemeType.LIGHT -> LightColorScheme
        ThemeType.TWILIGHT -> TwilightColorScheme
        else -> DarkColorScheme
    }

    if (!LocalInspectionMode.current) {
        val view = LocalView.current
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                colorScheme == LightColorScheme
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars =
                colorScheme == LightColorScheme
        }
    }

    CompositionLocalProvider(
        LocalDarkTheme provides isSystemInDarkTheme(),
        LocalTypography provides Typography,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            content = content,
        )
    }
}

object TodoTheme {
    val typography: TodoTypography
        @Composable
        get() = LocalTypography.current
}