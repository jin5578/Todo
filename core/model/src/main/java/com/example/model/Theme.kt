package com.example.model

enum class ThemeType(val themeName: String) {
    SYSTEM("system"),
    LIGHT("light"),
    DARK("dark"),
}

data class Theme(
    val type: ThemeType
)