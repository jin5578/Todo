package com.example.model

enum class ThemeType(val themeName: String) {
    SYSTEM("System"),
    SUN_RISE("SunRise"),
    SKY_BLUE("SkyBlue"),
    MIST_GRAY("MistGray"),
    MIDNIGHT_BLUE("MidnightBlue"),
    CHARCOAL_BLACK("CharcoalBlack"),
    DEEP_FOREST_GREEN("DeepForestGreen"),
}

data class Theme(
    val type: ThemeType
)