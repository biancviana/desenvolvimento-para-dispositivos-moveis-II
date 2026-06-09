package com.example.financeapp.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financeapp.domain.model.DataSourceType
import com.example.financeapp.domain.usecase.GetActiveDataSourceUseCase
import com.example.financeapp.domain.usecase.SetActiveDataSourceUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val getActiveDataSourceUseCase: GetActiveDataSourceUseCase,
    private val setActiveDataSourceUseCase: SetActiveDataSourceUseCase
) : ViewModel() {

    val currentSource = getActiveDataSourceUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = DataSourceType.ROOM
        )

    fun updateDataSource(type: DataSourceType) {
        viewModelScope.launch {
            setActiveDataSourceUseCase(type)
        }
    }
}