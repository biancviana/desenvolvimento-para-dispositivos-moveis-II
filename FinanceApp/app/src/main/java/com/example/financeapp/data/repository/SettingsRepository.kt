package com.example.financeapp.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.financeapp.domain.model.DataSourceType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepository(private val dataStore: DataStore<Preferences>) {
    private val DATA_SOURCE_KEY = stringPreferencesKey("data_source")

    val selectedDataSource: Flow<DataSourceType> =
        dataStore.data.map { pref ->
            val name = pref[DATA_SOURCE_KEY] ?: DataSourceType.ROOM.name
            DataSourceType.valueOf(name)
        }

    suspend fun saveDataSource(type: DataSourceType) {
        dataStore.edit {
            it[DATA_SOURCE_KEY] = type.name
        }
    }
}