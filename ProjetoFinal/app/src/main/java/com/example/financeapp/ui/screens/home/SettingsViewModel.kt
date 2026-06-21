package com.example.financeapp.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financeapp.data.firebase.repository.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val repository: SettingsRepository
) : ViewModel() {

    private val _currentSource = MutableStateFlow(DataSourceType.FIREBASE)
    val currentSource: StateFlow<DataSourceType> = _currentSource.asStateFlow()

    init {
        observeDataSource()
    }

    private fun observeDataSource() {
        viewModelScope.launch {
            repository.getDataSourceFlow().collect { sourceName ->
                val source = runCatching {
                    DataSourceType.valueOf(sourceName)
                }.getOrDefault(DataSourceType.FIREBASE)
                _currentSource.value = source
            }
        }
    }

    fun updateDataSource(source: DataSourceType) {
        viewModelScope.launch {
            _currentSource.value = source
            repository.saveDataSource(source.name)
        }
    }
}