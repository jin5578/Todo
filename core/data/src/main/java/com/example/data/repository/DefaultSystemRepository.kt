package com.example.data.repository

import com.example.data_api.repository.SystemRepository
import com.example.datastore.datasource.SystemPreferencesDataSource
import com.example.model.SortTask
import com.example.model.Theme
import com.example.model.ThemeType
import com.example.model.TimePicker
import com.example.model.addtask.AddTaskSystem
import com.example.model.calendar.CalendarSystem
import com.example.model.edittask.EditTaskSystem
import com.example.model.home.HomeSystem
import com.example.model.setting.SettingSystem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalTime
import javax.inject.Inject

internal class DefaultSystemRepository @Inject constructor(
    private val systemDataSource: SystemPreferencesDataSource
) : SystemRepository {
    override fun getTheme(): Flow<Theme> {
        return systemDataSource.systemData.map {
            it.theme.toTheme()
        }
    }

    override fun getHomeSystem(): Flow<HomeSystem> {
        return systemDataSource.systemData.map {
            val sleepTime = LocalTime.parse(it.sleepTime)
            val sortTask = it.sortTask.toSortTask()
            val theme = it.theme.toTheme()
            HomeSystem(
                sleepTime = sleepTime,
                sortTask = sortTask,
                theme = theme,
                buildVersion = it.buildVersion
            )
        }
    }

    override fun getSettingSystem(): Flow<SettingSystem> {
        return systemDataSource.systemData.map {
            val theme = it.theme.toTheme()
            val sleepTime = LocalTime.parse(it.sleepTime)
            val timePicker = it.timePicker.toTimePicker()
            SettingSystem(
                theme = theme,
                sleepTime = sleepTime,
                timePicker = timePicker,
                buildVersion = it.buildVersion
            )
        }
    }

    override fun getAddTaskSystem(): Flow<AddTaskSystem> {
        return systemDataSource.systemData.map {
            val timePicker = it.timePicker.toTimePicker()
            AddTaskSystem(
                timePicker = timePicker
            )
        }
    }

    override fun getEditTaskSystem(): Flow<EditTaskSystem> {
        return systemDataSource.systemData.map {
            val timePicker = it.timePicker.toTimePicker()
            EditTaskSystem(
                timePicker = timePicker
            )
        }
    }

    override fun getCalendarSystem(): Flow<CalendarSystem> {
        return systemDataSource.systemData.map {
            val sortTask = it.sortTask.toSortTask()
            CalendarSystem(
                sortTask = sortTask
            )
        }
    }

    override suspend fun updateSortTask(sortTask: SortTask) {
        systemDataSource.updateSortTask(sortTask.type)
    }

    override suspend fun updateTheme(themeType: ThemeType) {
        systemDataSource.updateTheme(themeType.themeName)
    }

    override suspend fun updateTimePicker(timePicker: TimePicker) {
        systemDataSource.updateTimePicker(timePicker.type)
    }

    override suspend fun updateBuildVersion(buildVersion: String) {
        systemDataSource.updateBuildVersion(buildVersion)
    }

    private fun String.toTheme() = when (this) {
        "System" -> Theme(type = ThemeType.SYSTEM)
        "SunRise" -> Theme(type = ThemeType.SUN_RISE)
        "SkyBlue" -> Theme(type = ThemeType.SKY_BLUE)
        "MistGray" -> Theme(type = ThemeType.MIST_GRAY)
        "MidnightBlue" -> Theme(type = ThemeType.MIDNIGHT_BLUE)
        "CharcoalBlack" -> Theme(type = ThemeType.CHARCOAL_BLACK)
        else -> Theme(type = ThemeType.DEEP_FOREST_GREEN)
    }

    private fun String.toSortTask() = when (this) {
        "Priority (Low to High)" -> SortTask.BY_PRIORITY_ASCENDING
        "Priority (High to Low)" -> SortTask.BY_PRIORITY_DESCENDING
        "Start Time (Latest at Bottom)" -> SortTask.BY_START_TIME_ASCENDING
        "Start Time (Latest at Top)" -> SortTask.BY_START_TIME_DESCENDING
        "Creation Time (Latest at Bottom)" -> SortTask.BY_CREATE_TIME_ASCENDING
        else -> SortTask.BY_CREATE_TIME_DESCENDING
    }

    private fun String.toTimePicker() = when (this) {
        "ScrollTimePicker" -> TimePicker.SCROLL_TIME_PICKER
        else -> TimePicker.CLOCK_TIME_PICKER
    }
}