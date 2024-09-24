package com.example.model.edittask

import com.example.model.Task

data class EditTask(
    val task: Task,
    val editTaskSystem: EditTaskSystem
)