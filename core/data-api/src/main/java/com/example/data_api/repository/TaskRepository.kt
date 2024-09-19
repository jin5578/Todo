package com.example.data_api.repository

import com.example.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getTodayTasks(): Flow<List<Task>>
    suspend fun getTaskById(taskId: Long): Task
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(task: Task)
}