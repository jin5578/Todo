package com.example.database.datasource

import com.example.database.TaskDatabase
import com.example.database.TaskEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class DefaultTaskDatabaseDataSource @Inject constructor(
    private val taskDatabase: TaskDatabase
) : TaskDatabaseDataSource {
    override fun getAllTask(): Flow<List<TaskEntity>> {
        return taskDatabase.taskDao().getAllTask()
    }

    override fun getTasksByDate(date: LocalDate): Flow<List<TaskEntity>> {
        return taskDatabase.taskDao().getTasksByDate(date.toString())
    }

    override fun getTaskCountByDate(date: LocalDate): Flow<Int> {
        return taskDatabase.taskDao().getTasksByDate(date.toString()).map { it.count() }
    }

    override fun getTasksByDateRange(
        fromDate: LocalDate,
        toDate: LocalDate
    ): Flow<List<TaskEntity>> {
        return taskDatabase
            .taskDao()
            .getTasksByEpochDayRange(
                fromDate = fromDate.toEpochDay(),
                toDate = toDate.toEpochDay()
            )
    }

    override fun getTasksByState(isCompleted: Boolean): Flow<List<TaskEntity>> {
        return taskDatabase.taskDao().getTasksByState(isCompleted)
    }

    override suspend fun getTaskById(taskId: Long): TaskEntity {
        return taskDatabase.taskDao().getTaskById(taskId)
    }

    override suspend fun insertTask(taskEntity: TaskEntity) {
        return taskDatabase.taskDao().insertTask(taskEntity)
    }

    override suspend fun updateTask(taskEntity: TaskEntity) {
        taskDatabase.taskDao().updateTask(taskEntity)
    }

    override suspend fun deleteTask(taskEntity: TaskEntity) {
        taskDatabase.taskDao().deleteTask(taskEntity)
    }
}