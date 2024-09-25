package com.example.utils

import com.example.model.Task

fun checkValidTask(
    task: Task,
): Pair<Boolean, String> {
    if (task.title.trim().isEmpty()) {
        return Pair(false, "Title can't be empty")
    }

    if (task.startTime <= task.endTime) {
        return Pair(false, "Please check the time")
    }

    return Pair(true, "Valid task")
}