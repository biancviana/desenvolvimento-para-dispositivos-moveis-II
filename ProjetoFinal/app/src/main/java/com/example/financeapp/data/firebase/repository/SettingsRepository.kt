package com.example.financeapp.data.firebase.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepository(private val dataStore: DataStore<Preferences>) {

    private companion object {
        val DATA_SOURCE_KEY = stringPreferencesKey("data_source_type")
    }

    fun getDataSourceFlow(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[DATA_SOURCE_KEY] ?: "FIREBASE"
        }
    }

    suspend fun saveDataSource(sourceName: String) {
        dataStore.edit { preferences ->
            preferences[DATA_SOURCE_KEY] = sourceName
        }
    }
}