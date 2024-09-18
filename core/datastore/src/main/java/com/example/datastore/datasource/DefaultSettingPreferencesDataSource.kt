package com.example.datastore.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.datastore.model.SettingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named

class DefaultSettingPreferencesDataSource @Inject constructor(
    @Named("setting") private val dataStore: DataStore<Preferences>,
) : SettingPreferencesDataSource {
    object PreferencesKey {
        val THEME_KEY = stringPreferencesKey("theme")
    }

    override val settingData: Flow<SettingData> = dataStore.data.map { preferences ->
        SettingData(
            theme = preferences[PreferencesKey.THEME_KEY] ?: DEFAULT_THEME
        )
    }

    companion object {
        private const val DEFAULT_THEME = "system"
    }
}