package com.example.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = "lock_datastore")

class LockDataStore @Inject constructor(
    private val context: Context
) {
    suspend fun setPinNumber(number: String) {
        context.dataStore.edit { pref ->
            pref[KEY_PIN_NUMBER] = number
        }
    }

    suspend fun getPinNumber(): String? {
        return context.dataStore.data.map { it[KEY_PIN_NUMBER] }.firstOrNull()
    }

    suspend fun clear() {
        context.dataStore.edit { pref ->
            pref.clear()
        }
    }

    companion object {
        private val KEY_PIN_NUMBER = stringPreferencesKey("pinNumber")
    }
}