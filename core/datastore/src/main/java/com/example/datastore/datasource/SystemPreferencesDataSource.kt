package com.example.datastore.datasource

import com.example.datastore.model.SystemData
import kotlinx.coroutines.flow.Flow

interface SystemPreferencesDataSource {
    val systemData: Flow<SystemData>
    suspend fun updateSortTask(sortTask: String)
    suspend fun updateTheme(theme: String)
    suspend fun updateTimePicker(timePicker: String)
}