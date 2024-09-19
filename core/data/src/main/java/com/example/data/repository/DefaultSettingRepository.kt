package com.example.data.repository

import com.example.data_api.repository.SettingRepository
import com.example.datastore.datasource.SettingPreferencesDataSource
import com.example.model.SortTask
import com.example.model.Theme
import com.example.model.ThemeType
import com.example.model.home.HomeSetting
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalTime
import javax.inject.Inject

internal class DefaultSettingRepository @Inject constructor(
    private val settingDataSource: SettingPreferencesDataSource
) : SettingRepository {
    override fun getTheme(): Flow<Theme> {
        return settingDataSource.settingData.map {
            it.theme.toTheme()
        }
    }

    override fun getHomeSetting(): Flow<HomeSetting> {
        return settingDataSource.settingData.map {
            val sleepTime = LocalTime.parse(it.sleepTime)
            val sortTask = it.sortTask.toSortTask()
            val theme = it.theme.toTheme()
            HomeSetting(
                sleepTime = sleepTime,
                sortTask = sortTask,
                theme = theme,
                buildVersion = it.buildVersion
            )
        }
    }

    override suspend fun updateSortTask(sortTask: SortTask) {
        settingDataSource.updateSortTask(sortTask.type)
    }

    private fun String.toTheme() = when (this) {
        "system" -> Theme(type = ThemeType.SYSTEM)
        "light" -> Theme(type = ThemeType.LIGHT)
        "twilight" -> Theme(type = ThemeType.TWILIGHT)
        else -> Theme(type = ThemeType.DARK)
    }

    private fun String.toSortTask() = when (this) {
        "Priority (Low to High)" -> SortTask.BY_PRIORITY_ASCENDING
        "Priority (High to Low)" -> SortTask.BY_PRIORITY_DESCENDING
        "Start Time (Latest at Bottom)" -> SortTask.BY_START_TIME_ASCENDING
        "Start Time (Latest at Top)" -> SortTask.BY_START_TIME_DESCENDING
        "Creation Time (Latest at Bottom)" -> SortTask.BY_CREATE_TIME_ASCENDING
        else -> SortTask.BY_CREATE_TIME_DESCENDING
    }
}