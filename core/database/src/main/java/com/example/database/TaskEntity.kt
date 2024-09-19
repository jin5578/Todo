package com.example.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.database.utils.LocalDateConverter
import com.example.database.utils.LocalTimeConverter
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "task")
@TypeConverters(
    LocalTimeConverter::class,
    LocalDateConverter::class,
)
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0L,
    @ColumnInfo(name = "uuid")
    val uuid: String,
    @ColumnInfo(name = "title")
    val title: String = "",
    @ColumnInfo(name = "isCompleted")
    val isCompleted: Boolean = false,
    @ColumnInfo(name = "startTime")
    val startTime: LocalTime = LocalTime.now(),
    @ColumnInfo(name = "endTime")
    val endTime: LocalTime = LocalTime.now(),
    @ColumnInfo(name = "date")
    val date: LocalDate = LocalDate.now(),
    @ColumnInfo(name = "priority")
    val priority: Int = 0,
)