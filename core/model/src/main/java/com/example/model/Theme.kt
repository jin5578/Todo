package com.example.model

enum class ThemeType(val themeName: String) {
    SYSTEM("system"),
    LIGHT("light"),
    TWILIGHT("twilight"),
    DARK("dark"),
}

data class Theme(
    val type: ThemeType
)