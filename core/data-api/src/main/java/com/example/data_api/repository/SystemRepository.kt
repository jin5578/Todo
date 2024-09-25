package com.example.data_api.repository

import com.example.model.SortTask
import com.example.model.Theme
import com.example.model.ThemeType
import com.example.model.TimePicker
import com.example.model.addtask.AddTaskSystem
import com.example.model.edittask.EditTaskSystem
import com.example.model.home.HomeSystem
import com.example.model.setting.SettingSystem
import kotlinx.coroutines.flow.Flow

interface SystemRepository {
    fun getTheme(): Flow<Theme>
    fun getHomeSystem(): Flow<HomeSystem>
    fun getSettingSystem(): Flow<SettingSystem>
    fun getAddTaskSystem(): Flow<AddTaskSystem>
    fun getEditTaskSystem(): Flow<EditTaskSystem>
    suspend fun updateSortTask(sortTask: SortTask)
    suspend fun updateTheme(themeType: ThemeType)
    suspend fun updateTimePicker(timePicker: TimePicker)
    suspend fun updateBuildVersion(buildVersion: String)
}