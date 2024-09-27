package com.example.manage_categories.model

import androidx.compose.ui.graphics.Color
import com.example.design_system.theme.Blue
import com.example.design_system.theme.Green
import com.example.design_system.theme.Indigo
import com.example.design_system.theme.Orange
import com.example.design_system.theme.Red
import com.example.design_system.theme.Violet
import com.example.design_system.theme.Yellow

enum class CategoryColor(
    val colorName: String,
    val color: Color,
) {
    RED(
        colorName = "Red",
        color = Red
    ),
    ORANGE(
        colorName = "Orange",
        color = Orange
    ),
    YELLOW(
        colorName = "Yellow",
        color = Yellow
    ),
    GREEN(
        colorName = "Green",
        color = Green
    ),
    BLUE(
        colorName = "Blue",
        color = Blue
    ),
    INDIGO(
        colorName = "Indigo",
        color = Indigo
    ),
    VIOLET(
        colorName = "Violet",
        color = Violet
    ),
}