package com.example.datastore.datasource

import com.example.datastore.model.SettingData
import kotlinx.coroutines.flow.Flow

interface SettingPreferencesDataSource {
    val settingData: Flow<SettingData>
    suspend fun updateSortTask(sortTask: String)
}