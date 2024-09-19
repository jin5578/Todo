package com.example.model.setting

import com.example.model.Theme
import com.example.model.TimePicker
import java.time.LocalTime

data class SettingSystem(
    val theme: Theme,
    val sleepTime: LocalTime,
    val timePicker: TimePicker,
    val buildVersion: String,
)