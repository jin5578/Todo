package com.example.database.datasource

import com.example.database.TaskEntity
import kotlinx.coroutines.flow.Flow

interface TaskDatabaseDataSource {
    fun getTodayTasks(): Flow<List<TaskEntity>>
    suspend fun getTaskById(taskId: Long): TaskEntity
    suspend fun updateTask(taskEntity: TaskEntity)
    suspend fun deleteTask(taskEntity: TaskEntity)
}