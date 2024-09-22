package com.example.database.datasource

import com.example.database.TaskEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface TaskDatabaseDataSource {
    fun getTodayTasks(): Flow<List<TaskEntity>>
    fun getTaskCountByDate(date: LocalDate): Flow<Int>
    suspend fun getTaskById(taskId: Long): TaskEntity
    suspend fun insertTask(taskEntity: TaskEntity)
    suspend fun updateTask(taskEntity: TaskEntity)
    suspend fun deleteTask(taskEntity: TaskEntity)
}