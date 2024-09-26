package com.example.model

enum class SortTask(val type: String) {
    BY_PRIORITY_ASCENDING("Priority (Low to High)"),
    BY_PRIORITY_DESCENDING("Priority (High to Low)"),
    BY_TIME_ASCENDING("Time (Latest at Bottom)"),
    BY_TIME_DESCENDING("Time (Latest at Top)"),
    BY_CREATE_TIME_ASCENDING("Creation Time (Latest at Bottom)"),
    BY_CREATE_TIME_DESCENDING("Creation Time (Latest at Top)"),
}