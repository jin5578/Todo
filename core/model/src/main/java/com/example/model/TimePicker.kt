package com.example.model

enum class TimePicker(
    val type: String,
    val timePickerName: String,
) {
    SCROLL_TIME_PICKER(
        type = "ScrollTimePicker",
        timePickerName = "Scroll time picker"
    ),
    CLOCK_TIME_PICKER(
        type = "ClockTimePicker",
        timePickerName = "Clock time picker"
    )
}