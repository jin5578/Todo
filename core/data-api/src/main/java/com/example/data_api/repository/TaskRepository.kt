package com.example.data_api.repository

import com.example.model.Task
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface TaskRepository {
    fun getAllTask(): Flow<List<Task>>
    fun getTasksByDate(date: LocalDate): Flow<List<Task>>
    fun getTaskCountByDate(date: LocalDate): Flow<Int>
    fun getTasksByDateRange(fromDate: LocalDate, toDate: LocalDate): Flow<List<Task>>
    fun getTasksByState(isCompleted: Boolean): Flow<List<Task>>

    suspend fun getTaskById(taskId: Long): Task
    suspend fun insertTask(task: Task)
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(task: Task)
}