package com.example.datastore.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.datastore.model.SettingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalTime
import javax.inject.Inject
import javax.inject.Named

class DefaultSettingPreferencesDataSource @Inject constructor(
    @Named("setting") private val dataStore: DataStore<Preferences>,
) : SettingPreferencesDataSource {
    object PreferencesKey {
        val SLEEP_TIME_KEY = stringPreferencesKey("sleep_time_key")
        val SORT_TASK_KEY = stringPreferencesKey("sort_task_key")
        val THEME_KEY = stringPreferencesKey("theme")
        val TIME_PICKER_KEY = stringPreferencesKey("time_picker_key")
        val BUILD_VERSION_KEY = stringPreferencesKey("build_version_key")
    }

    override val settingData: Flow<SettingData> = dataStore.data.map { preferences ->
        SettingData(
            sleepTime = preferences[PreferencesKey.SLEEP_TIME_KEY] ?: DEFAULT_SLEEP_TIME,
            sortTask = preferences[PreferencesKey.SORT_TASK_KEY] ?: DEFAULT_SORT_TASK,
            theme = preferences[PreferencesKey.THEME_KEY] ?: DEFAULT_THEME,
            timePicker = preferences[PreferencesKey.TIME_PICKER_KEY] ?: DEFAULT_TIME_PICKER,
            buildVersion = preferences[PreferencesKey.BUILD_VERSION_KEY] ?: DEFAULT_BUILD_VERSION,
        )
    }

    override suspend fun updateSortTask(sortTask: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.SORT_TASK_KEY] = sortTask
        }
    }

    companion object {
        private val DEFAULT_SLEEP_TIME = LocalTime.of(23, 59).toString()
        private const val DEFAULT_SORT_TASK = "Creation Time (Latest at Top)"
        private const val DEFAULT_THEME = "system"
        private const val DEFAULT_TIME_PICKER = "clockTimePicker"
        private const val DEFAULT_BUILD_VERSION = "1.0.0"
    }
}