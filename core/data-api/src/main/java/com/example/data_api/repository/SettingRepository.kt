package com.example.data_api.repository

import com.example.model.SortTask
import com.example.model.Theme
import com.example.model.home.HomeSetting
import kotlinx.coroutines.flow.Flow

interface SettingRepository {
    fun getTheme(): Flow<Theme>
    fun getHomeSetting(): Flow<HomeSetting>
    suspend fun updateSortTask(sortTask: SortTask)
}