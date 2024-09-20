package com.example.model.addtask

import java.time.LocalDate

data class AddTask(
    val date: LocalDate,
    val taskCount: Int,
    val addTaskSystem: AddTaskSystem
)