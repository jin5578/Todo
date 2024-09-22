package com.example.data.repository

import com.example.data_api.repository.TaskRepository
import com.example.database.TaskEntity
import com.example.database.datasource.TaskDatabaseDataSource
import com.example.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

internal class DefaultTaskRepository @Inject constructor(
    private val taskDataSource: TaskDatabaseDataSource,
) : TaskRepository {
    override fun getTodayTasks(): Flow<List<Task>> {
        return taskDataSource.getTodayTasks().map { entities ->
            entities.map { entity ->
                entity.toTask()
            }
        }
    }

    override fun getTaskCountByDate(date: LocalDate): Flow<Int> {
        return taskDataSource.getTaskCountByDate(date)
    }

    override suspend fun getTaskById(taskId: Long): Task {
        return taskDataSource.getTaskById(taskId).toTask()
    }

    override suspend fun insertTask(task: Task) {
        val entity = task.toTaskEntity()
        return taskDataSource.insertTask(entity)
    }

    override suspend fun updateTask(task: Task) {
        val entity = task.toTaskEntity()
        taskDataSource.updateTask(entity)
    }

    override suspend fun deleteTask(task: Task) {
        val entity = task.toTaskEntity()
        taskDataSource.deleteTask(entity)
    }

    private fun TaskEntity.toTask() = Task(
        id = this.id,
        uuid = this.uuid,
        title = this.title,
        isCompleted = this.isCompleted,
        startTime = this.startTime,
        endTime = this.endTime,
        date = this.date,
        memo = this.memo,
        priority = this.priority
    )

    private fun Task.toTaskEntity() = TaskEntity(
        id = this.id,
        uuid = this.uuid,
        title = this.title,
        isCompleted = this.isCompleted,
        startTime = this.startTime,
        endTime = this.endTime,
        date = this.date,
        memo = this.memo,
        priority = this.priority
    )
}