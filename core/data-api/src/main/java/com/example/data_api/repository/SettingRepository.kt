package com.example.data_api.repository

import com.example.model.Theme
import kotlinx.coroutines.flow.Flow

interface SettingRepository {
    fun getTheme(): Flow<Theme>
}