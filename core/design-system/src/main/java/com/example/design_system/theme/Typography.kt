package com.example.design_system.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.design_system.R

private val Sans = FontFamily(
    Font(R.font.sans_light, FontWeight.Light),
    Font(R.font.sans_regular, FontWeight.Normal),
    Font(R.font.sans_medium, FontWeight.Medium),
    Font(R.font.sans_bold, FontWeight.SemiBold)
)

private val SansLight = TextStyle(
    fontFamily = Sans,
    fontWeight = FontWeight.Light
)

private val SansRegular = TextStyle(
    fontFamily = Sans,
    fontWeight = FontWeight.Normal
)

private val SansMedium = TextStyle(
    fontFamily = Sans,
    fontWeight = FontWeight.Medium
)

private val SansBold = TextStyle(
    fontFamily = Sans,
    fontWeight = FontWeight.Bold
)

internal val Typography = TodoTypography(
    headlineLarge = SansBold.copy(
        fontSize = 24.sp,
    ),
    headlineMedium = SansBold.copy(
        fontSize = 20.sp,
    ),
    headlineSmall = SansBold.copy(
        fontSize = 16.sp,
    ),
    infoTextStyle = SansMedium.copy(
        fontSize = 18.sp,
    ),
    infoDescTextStyle = SansRegular.copy(
        fontSize = 14.sp,
    ),
    taskTextStyle = SansRegular.copy(
        fontSize = 16.sp,
    ),
    taskDescTextStyle = SansRegular.copy(
        fontSize = 12.sp,
    ),
    timerTextStyle = SansRegular.copy(
        fontSize = 42.sp,
    ),
    settingItemTextStyle = SansRegular.copy(
        fontSize = 18.sp,
    ),
    durationTextStyle = SansRegular.copy(
        fontSize = 20.sp,
    ),
)

@Immutable
data class TodoTypography(
    val headlineLarge: TextStyle,
    val headlineMedium: TextStyle,
    val headlineSmall: TextStyle,

    val infoTextStyle: TextStyle,
    val infoDescTextStyle: TextStyle,

    val taskTextStyle: TextStyle,
    val taskDescTextStyle: TextStyle,

    val timerTextStyle: TextStyle,
    val settingItemTextStyle: TextStyle,
    val durationTextStyle: TextStyle,
)

val LocalTypography = staticCompositionLocalOf {
    TodoTypography(
        headlineLarge = SansBold,
        headlineMedium = SansBold,
        headlineSmall = SansBold,
        infoTextStyle = SansMedium,
        infoDescTextStyle = SansRegular,
        taskTextStyle = SansRegular,
        taskDescTextStyle = SansRegular,
        timerTextStyle = SansRegular,
        settingItemTextStyle = SansRegular,
        durationTextStyle = SansRegular,
    )
}