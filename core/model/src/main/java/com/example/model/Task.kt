package com.example.model

import java.time.LocalDate
import java.time.LocalTime

data class Task(
    val id: Long = 0L,
    val uuid: String,
    val title: String,
    val isCompleted: Boolean,
    val time: LocalTime,
    val date: LocalDate,
    val memo: String,
    val priority: Int = 0,
)