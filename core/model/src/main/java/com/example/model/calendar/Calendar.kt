package com.example.model.calendar

import com.example.model.Task

data class Calendar(
    val tasks: List<Task>,
    val calendarSystem: CalendarSystem,
)