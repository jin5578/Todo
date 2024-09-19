package com.example.model.home

import com.example.model.SortTask
import com.example.model.Theme
import java.time.LocalTime

data class HomeSetting(
    val sleepTime: LocalTime,
    val sortTask: SortTask,
    val theme: Theme,
    val buildVersion: String,
)