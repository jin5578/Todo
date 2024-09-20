package com.example.model

enum class ThemeType(val themeName: String) {
    SYSTEM("system"),
    SUN_RISE("sunRise"),
    SKY_BLUE("skyBlue"),
    MIST_GRAY("mistGray"),
    MIDNIGHT_BLUE("midnightBlue"),
    CHARCOAL_BLACK("charcoalBlack"),
    DEEP_FOREST_GREEN("deepForestGreen"),
}

data class Theme(
    val type: ThemeType
)