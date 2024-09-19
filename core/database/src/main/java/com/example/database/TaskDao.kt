package com.example.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)

    @Query("DELETE FROM task")
    suspend fun deleteAllTasks()

    @Query("SELECT * FROM task")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM task WHERE date = :selectedDate")
    fun getTasksByDate(selectedDate: String): Flow<List<TaskEntity>>

    @Query("SELECT * FROM task WHERE id=:id")
    suspend fun getTaskById(id: Long): TaskEntity
}