package com.example.database.datasource

import com.example.database.TaskEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface TaskDatabaseDataSource {
    fun getAllTask(): Flow<List<TaskEntity>>
    fun getTasksByDate(date: LocalDate): Flow<List<TaskEntity>>
    fun getTaskCountByDate(date: LocalDate): Flow<Int>
    fun getTasksByDateRange(fromDate: LocalDate, toDate: LocalDate): Flow<List<TaskEntity>>
    fun getTasksByState(isCompleted: Boolean): Flow<List<TaskEntity>>
    fun getFlowTaskById(taskId: Long): Flow<TaskEntity>

    suspend fun getTaskById(taskId: Long): TaskEntity
    suspend fun insertTask(taskEntity: TaskEntity)
    suspend fun updateTask(taskEntity: TaskEntity)
    suspend fun deleteTask(taskEntity: TaskEntity)
}