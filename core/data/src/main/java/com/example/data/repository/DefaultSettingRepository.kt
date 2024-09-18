package com.example.data.repository

import com.example.data_api.repository.SettingRepository
import com.example.datastore.datasource.SettingPreferencesDataSource
import com.example.model.Theme
import com.example.model.ThemeType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class DefaultSettingRepository @Inject constructor(
    private val settingDataSource: SettingPreferencesDataSource
) : SettingRepository {
    override fun getTheme(): Flow<Theme> {
        return settingDataSource.settingData.map {
            it.theme.toTheme()
        }
    }

    private fun String.toTheme() = when (this) {
        "system" -> Theme(type = ThemeType.SYSTEM)
        "light" -> Theme(type = ThemeType.LIGHT)
        "twilight" -> Theme(type = ThemeType.TWILIGHT)
        else -> Theme(type = ThemeType.DARK)
    }
}