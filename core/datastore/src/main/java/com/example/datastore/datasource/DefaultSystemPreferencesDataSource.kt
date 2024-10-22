package com.example.datastore.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.datastore.model.SystemData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalTime
import javax.inject.Inject
import javax.inject.Named

class DefaultSystemPreferencesDataSource @Inject constructor(
    @Named("system") private val dataStore: DataStore<Preferences>,
) : SystemPreferencesDataSource {
    object PreferencesKey {
        val SLEEP_TIME_KEY = stringPreferencesKey("sleep_time_key")
        val SORT_TASK_KEY = stringPreferencesKey("sort_task_key")
        val THEME_KEY = stringPreferencesKey("theme")
        val TIME_PICKER_KEY = stringPreferencesKey("time_picker_key")
        val BUILD_VERSION_KEY = stringPreferencesKey("build_version_key")
        val NOTIFICATION_STATE_KEY = booleanPreferencesKey("notification_state_key")
    }

    override val systemData: Flow<SystemData> = dataStore.data.map { preferences ->
        SystemData(
            sleepTime = preferences[PreferencesKey.SLEEP_TIME_KEY] ?: DEFAULT_SLEEP_TIME,
            sortTask = preferences[PreferencesKey.SORT_TASK_KEY] ?: DEFAULT_SORT_TASK,
            theme = preferences[PreferencesKey.THEME_KEY] ?: DEFAULT_THEME,
            timePicker = preferences[PreferencesKey.TIME_PICKER_KEY] ?: DEFAULT_TIME_PICKER,
            buildVersion = preferences[PreferencesKey.BUILD_VERSION_KEY] ?: DEFAULT_BUILD_VERSION,
            notificationState = preferences[PreferencesKey.NOTIFICATION_STATE_KEY]
                ?: DEFAULT_NOTIFICATION_STATE,
        )
    }

    override suspend fun updateSortTask(sortTask: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.SORT_TASK_KEY] = sortTask
        }
    }

    override suspend fun updateTheme(theme: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.THEME_KEY] = theme
        }
    }

    override suspend fun updateTimePicker(timePicker: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.TIME_PICKER_KEY] = timePicker
        }
    }

    override suspend fun updateBuildVersion(buildVersion: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.BUILD_VERSION_KEY] = buildVersion
        }
    }

    override suspend fun updateNotificationState(isEnabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.NOTIFICATION_STATE_KEY] = isEnabled
        }
    }

    companion object {
        private val DEFAULT_SLEEP_TIME = LocalTime.of(23, 59).toString()
        private const val DEFAULT_SORT_TASK = "Creation Time (Latest at Top)"
        private const val DEFAULT_THEME = "System"
        private const val DEFAULT_TIME_PICKER = "ClockTimePicker"
        private const val DEFAULT_BUILD_VERSION = "1.0.0"
        private const val DEFAULT_NOTIFICATION_STATE = false
    }
}