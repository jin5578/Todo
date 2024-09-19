package com.example.utils

import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun getTaskTotalTime(
    startTime: LocalTime,
    endTime: LocalTime,
): String {
    val defaultFormat = DateTimeFormatter.ofPattern("hh:mm a", Locale.US)
    val startTimeFormat = startTime.format(defaultFormat)
    val endTimeFormat = endTime.format(defaultFormat)
    return "$startTimeFormat - $endTimeFormat"
}