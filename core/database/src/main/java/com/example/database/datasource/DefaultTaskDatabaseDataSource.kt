package com.example.database.datasource

import com.example.database.TaskDatabase
import com.example.database.TaskEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class DefaultTaskDatabaseDataSource @Inject constructor(
    private val taskDatabase: TaskDatabase
) : TaskDatabaseDataSource {
    override fun getTodayTasks(): Flow<List<TaskEntity>> {
        return taskDatabase.taskDao().getTasksByDate(LocalDate.now().toString())
    }

    override suspend fun getTaskById(taskId: Long): TaskEntity {
        return taskDatabase.taskDao().getTaskById(taskId)
    }

    override suspend fun updateTask(taskEntity: TaskEntity) {
        taskDatabase.taskDao().updateTask(taskEntity)
    }

    override suspend fun deleteTask(taskEntity: TaskEntity) {
        taskDatabase.taskDao().deleteTask(taskEntity)
    }
}